package info.paveway.mapmemo;

import info.paveway.loader.HttpGetLoader;
import info.paveway.loader.HttpLoaderCallbacks;
import info.paveway.log.Logger;
import info.paveway.loader.OnReceiveResponseListener;
import info.paveway.mapmemo.CommonConstants.Language;
import info.paveway.mapmemo.CommonConstants.LoaderId;
import info.paveway.mapmemo.CommonConstants.ParamKey;
import info.paveway.mapmemo.CommonConstants.PlaceResponseStatus;
import info.paveway.mapmemo.CommonConstants.Url;
import info.paveway.mapmemo.data.PlaceData;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

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

    /** プレースデータマップ */
    private static Map<String, PlaceData> mPlaceDataMap = new HashMap<String, PlaceData>();

    /**
     * 生成された時に呼び出される。
     *
     * @param savedInstanceState 保存された時のインスタンスの状態
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // スーパークラスのメソッドを呼び出す。
        super.onCreate(savedInstanceState);

        // レイアウトを設定する。
        setContentView(R.layout.activity_main);

        // マップフラグメントを取得する。
        MapFragment mapFragment = (MapFragment)(getFragmentManager().findFragmentById(R.id.map));
        try {
            // マップオブジェクトを取得する。
            mGoogleMap = mapFragment.getMap();

            // アクティビティが初めて生成された場合
            if (null == savedInstanceState) {
                // フラグメントを再初期化しないように設定する。
                mapFragment.setRetainInstance(true);

                // 地図の初期設定を行う。
                mapInit();
            }
        } catch (Exception e) {
            mLogger.e(e);
            // 終了する。
            finish();
            return;
        }
    }

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
            String apiKey = getResources().getString(R.string.place_api_key);
            String url = createGetPlaceDataUrl(apiKey, latlng, Language.JA, 100, false, "food");
            mLogger.d("URL=[" + url + "]");
            params.putString(ParamKey.URL, url);

            // HTTP GETローダーをロードする。
            getSupportLoaderManager().restartLoader(
                    LoaderId.PLACES, params, new HttpLoaderCallbacks(
                            MainActivity.this, new HttpOnReceiveResponseListener(), HttpGetLoader.class));

            mLogger.d("OUT(OK)");
        }
    }

    /**
     * プレースデータ取得URLを生成する。
     *
     * @param key APIキー
     * @param latlng 位置情報
     * @param language 言語
     * @param radius 検索範囲(メートル単位)
     * @param sensor GPSセンサー有無(true:有 / false:無)
     * @param types 検索タイプ
     * @return プレースデータ取得URL文字列
     */
    private String createGetPlaceDataUrl(String key, LatLng latlng, String language, int radius, boolean sensor, String types) {
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
                String status = json.getString(ParamKey.STATUS);

                // ステータスがOKではない場合
                if (!PlaceResponseStatus.OK.equals(status)) {
                    // 終了する。
                    Toast.makeText(MainActivity.this, "status=[" + status + "]", Toast.LENGTH_SHORT).show();
                    return;
                }

                // プレースデータマップをクリアする。
                clearPlaceDataMap();

                JSONArray results = json.getJSONArray(ParamKey.RESULTS);

                // 結果数を取得する。
                int resultsNum = results.length();

                // 結果数分繰り返す。
                for (int i = 0; i < resultsNum; i++) {
                    // プレースデータを生成する。
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

                    // マーカーを設定する。
                    setPlaceMarker(placeData);

                    // プレースデータマップに設定する。
                    mPlaceDataMap.put(placeData.getName(), placeData);
                }
            } catch (JSONException e) {
                mLogger.e(e);
            }

            mLogger.d("OUT(OK)");
        }
    }

    /**
     * プレースマーカーを設定する。
     *
     * @param data プレースデータ
     */
    private void setPlaceMarker(PlaceData data) {
        mLogger.d("IN");

        MarkerOptions options = new MarkerOptions();
        options.position(new LatLng(data.getLat(), data.getLng()));
        options.title(data.getName());
        options.snippet(data.getVicinity());
        data.setMarker(mGoogleMap.addMarker(options));

        mLogger.d("OUT(OK)");
    }

    /**
     * プレースデータマップをクリアする。
     *
     */
    private void clearPlaceDataMap() {
        mLogger.d("IN");

        Iterator<String> itr = mPlaceDataMap.keySet().iterator();
        while (itr.hasNext()) {
            String name = itr.next();
            Marker marker = mPlaceDataMap.get(name).getMarker();
            marker.remove();
        }
        mPlaceDataMap.clear();

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
