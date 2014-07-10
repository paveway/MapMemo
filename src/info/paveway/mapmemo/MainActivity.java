package info.paveway.mapmemo;

import info.paveway.loader.HttpGetLoaderCallbacks;
import info.paveway.log.Logger;
import info.paveway.loader.OnReceiveResponseListener;
import info.paveway.mapmemo.CommonConstants.LoaderId;
import info.paveway.mapmemo.CommonConstants.ParamKey;
import info.paveway.mapmemo.CommonConstants.Url;
import info.paveway.mapmemo.data.PlaceData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * マップメモ
 * メイン画面クラス
 *
 * @version 1.0 新規作成
 *
 */
public class MainActivity extends ActionBarActivity {

    /** ロガー */
    private Logger mLogger = new Logger(MainActivity.class);

    /** Googleマップ */
    private GoogleMap mGoogleMap;

    private List<PlaceData> mPlaceDataList = new ArrayList<PlaceData>();

    /** マーカーマップ */
    private static Map<String, Marker> mMarkerMap = new HashMap<String, Marker>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // マップフラグメントを取得する。
        MapFragment mapFragment = (MapFragment)(getFragmentManager().findFragmentById(R.id.map));
        try {
            // マップオブジェクトを取得する。
            mGoogleMap = mapFragment.getMap();

            // Activityが初めて生成された場合
            if (null == savedInstanceState) {
                // フラグメントを保存する。
                mapFragment.setRetainInstance(true);

                // 地図の初期設定を行う。
                mapInit();
            }
        } catch (Exception e) {
            mLogger.e(e);
            finish();
            return;
        }
    }

    /**************************************************************************/
    /*** 内部メソッド                                                       ***/
    /**************************************************************************/
    /**
     * 地図の初期化を行う。
     */
    private void mapInit() {
        mLogger.d("IN");

        // 地図タイプを設定する。
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // 現在位置ボタンの表示する。
        mGoogleMap.setMyLocationEnabled(true);

        mGoogleMap.setOnMapClickListener(new MapOnMapClickListener());

        // マーカークリックリスナーを設定する。
        mGoogleMap.setOnMarkerClickListener(new MapOnMarkerClickListener());

        mLogger.d("OUT(OK)");
    }


    /**************************************************************************/
    /**
     * マップクリックリスナークラス
     *
     */
    private class MapOnMapClickListener implements OnMapClickListener {

        /** ロガー */
        private Logger mLogger = new Logger(MapOnMarkerClickListener.class);

        /**
         * マップがクリックされた時に呼び出される。
         *
         * @param latlng 緯度経度データ
         */
        @Override
        public void onMapClick(LatLng latlng) {
            mLogger.d("IN");

            // パラメータを生成する。
            Bundle params = new Bundle();
            String url = createUrl(CommonConstants.PLACES_API_KEY, latlng, "ja", 500, false, "food");
            params.putString(ParamKey.URL, url);

            // HTTP GETローダーをロードする。
            getSupportLoaderManager().restartLoader(
                    LoaderId.PLACES, params, new HttpGetLoaderCallbacks(
                            MainActivity.this, new HttpOnReceiveResponseListener()));

            mLogger.d("OUT(OK)");
        }
    }

    private String createUrl(String key, LatLng latlng, String language, int radius, boolean sensor, String types) {
        StringBuilder sb = new StringBuilder();
        sb.append(Url.BASE);
        sb.append("?key=" + key);
        sb.append("&location=" + String.valueOf(latlng.latitude) + "," + String.valueOf(latlng.longitude));
        sb.append("&language=" + language);
        sb.append("&radius=" + String.valueOf(radius));
        sb.append("&sensor=" + String.valueOf(sensor));
        sb.append("&types=" + types);
        return sb.toString();
    }

    /**************************************************************************/
    /**
     * HTTPレスポンス受信リスナークラス
     *
     */
    private class HttpOnReceiveResponseListener implements OnReceiveResponseListener {

        /** ロガー */
        private Logger mLogger = new Logger(HttpOnReceiveResponseListener.class);

        /**
         * レスポンスを受信した時に呼び出される。
         *
         * @param response レスポンス文字列
         * @param bundle バンドル
         */
        @Override
        public void onReceive(String response, Bundle bundle) {
            mLogger.d("IN");

            try {
                // JSON文字列を生成する。
                JSONObject json = new JSONObject(response);
                JSONArray results = json.getJSONArray(ParamKey.RESULTS);
                int resultsNum = results.length();
                for (int i = 0; i < resultsNum; i++) {
                    PlaceData placeData = new PlaceData();

                    JSONObject result = results.getJSONObject(i);
                    JSONObject geometry = result.getJSONObject(ParamKey.GEOMETRY);
                    JSONObject location = geometry.getJSONObject(ParamKey.LOCATION);

                    placeData.setLat(      location.getDouble(ParamKey.LAT));
                    placeData.setLng(      location.getDouble(ParamKey.LNG));
                    placeData.setIcon(     result.getString(  ParamKey.ICON));
                    placeData.setId(       result.getString(  ParamKey.ID));
                    placeData.setName(     result.getString(  ParamKey.NAME));
                    placeData.setPlaceId(  result.getString(  ParamKey.PLACE_ID));
                    placeData.setReference(result.getString(  ParamKey.REFERENCE));
                    placeData.setScope(    result.getString(  ParamKey.SCOPE));
                    placeData.setTypes(    result.getString(  ParamKey.TYPES));
                    placeData.setVicinity( result.getString(  ParamKey.VICINITY));

                    setPlaceMarker(placeData);

                    mPlaceDataList.add(placeData);
                }
            } catch (JSONException e) {
                mLogger.e(e);
            }

            mLogger.d("OUT(OK)");
        }
    }

    /**
     * 位置マーカーを設定する。
     *
     * @param data 位置データ
     */
    private void setPlaceMarker(PlaceData data) {
        mLogger.d("IN");

        String name = data.getName();

        // IDのマーカーを取得する。
        Marker marker = mMarkerMap.get(name);
        // 取得できた場合
        if (null != marker) {
            // マーカーを削除する。
            mLogger.d("Marker exist.");
            marker.remove();
            mMarkerMap.remove(name);

        } else {
            mLogger.d("Marker not exist.");
        }

        MarkerOptions options = new MarkerOptions();
        options.position(new LatLng(data.getLat(), data.getLng()));
        options.title(name);
        options.snippet(data.getVicinity());
        mMarkerMap.put(name, mGoogleMap.addMarker(options));

        mLogger.d("OUT(OK)");
    }

    /**
     * 位置マーカーをクリアする。
     *
     */
    private void clearPlaceMarker() {
        mLogger.d("IN");

        Iterator<String> itr = mMarkerMap.keySet().iterator();
        while (itr.hasNext()) {
            String name = itr.next();
            Marker marker = mMarkerMap.get(name);
            marker.remove();
            mMarkerMap.remove(name);
        }

        mLogger.d("OUT(OK)");
    }

    /**************************************************************************/
    /**
     * マップマーカークリックリスナークラス
     *
     */
    private class MapOnMarkerClickListener implements OnMarkerClickListener {

        /** ロガー */
        private Logger mLogger = new Logger(MapOnMarkerClickListener.class);

        /**
         * マーカーをクリックした時に呼び出される。
         *
         * @param marker マーカー
         */
        @Override
        public boolean onMarkerClick(Marker marker) {
            mLogger.d("IN");

            // 位置情報を表示する。
//            marker.setSnippet(marker.getPosition().toString());

            mLogger.d("OUT(OK)");
            return false;
        }
    }
}
