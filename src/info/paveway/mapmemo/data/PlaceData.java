package info.paveway.mapmemo.data;

import java.io.Serializable;

import com.google.android.gms.maps.model.Marker;

/**
 * マップメモ
 * プレースデータクラス
 *
 * @version 1.0 新規作成
 *
 */
@SuppressWarnings("serial")
public class PlaceData implements Serializable {

    /** ID */
    private String mId;

    /** 名前 */
    private String mName;

    /** プレースID */
    private String mPlaceId;

    /** 緯度 */
    private double mLat;

    /** 経度 */
    private double mLng;

    /** アイコン */
    private String mIcon;

    /** リファレンス */
    private String mReference;

    /** スコープ */
    private String mScope;

    /** タイプ */
    private String mTypes;

    /** 周辺 */
    private String mVicinity;

    /** マーカー */
    private Marker mMarker;

    /**
     * IDを設定する。
     *
     * @param id ID
     */
    public void setId(String id) {
        mId = id;
    }

    /**
     * IDを返却する。
     *
     * @return ID
     */
    public String getId() {
        return mId;
    }

    /**
     * 名前を設定する。
     *
     * @param name 名前
     */
    public void setName(String name) {
        mName = name;
    }

    /**
     * 名前を返却する。
     *
     * @return 名前
     */
    public String getName() {
        return mName;
    }

    /**
     * プレースIDを設定する。
     *
     * @param placeId プレースID
     */
    public void setPlaceId(String placeId) {
        mPlaceId = placeId;
    }

    /**
     * プレースIDを返却する。
     *
     * @return プレースID
     */
    public String getPlaceId() {
        return mPlaceId;
    }

    /**
     * 緯度を設定する。
     *
     * @param lat 緯度
     */
    public void setLat(double lat) {
        mLat = lat;
    }

    /**
     * 緯度を返却する。
     *
     * @return 緯度
     */
    public double getLat() {
        return mLat;
    }

    /**
     * 経度を設定する。
     *
     * @param lng 経度
     */
    public void setLng(double lng) {
        mLng = lng;
    }

    /**
     * 経度を返却する。
     *
     * @return 経度
     */
    public double getLng() {
        return mLng;
    }

    /**
     * アイコンURLを設定する。
     *
     * @param icon アイコンURL
     */
    public void setIcon(String icon) {
        mIcon = icon;
    }

    /**
     * アイコンURLを返却する。
     *
     * @return アイコンURL
     */
    public String getIcon() {
        return mIcon;
    }

    /**
     * リファレンスを設定する。
     *
     * @param reference リファレンス
     */
    public void setReference(String reference) {
        mReference = reference;
    }

    /**
     * リファレンスを返却する。
     *
     * @return リファレンス
     */
    public String getReference() {
        return mReference;
    }

    /**
     * スコープを設定する。
     *
     * @param scope スコープ
     */
    public void setScope(String scope) {
        mScope = scope;
    }

    /**
     * スコープを返却する。
     *
     * @return スコープ
     */
    public String getScope() {
        return mScope;
    }

    /**
     * タイプを設定する。
     *
     * @param types タイプ
     */
    public void setTypes(String types) {
        mTypes = types;
    }

    /**
     * タイプを返却する。
     *
     * @return タイプ
     */
    public String getTypes() {
        return mTypes;
    }

    /**
     * 周辺を設定する。
     *
     * @param vicinity 周辺
     */
    public void setVicinity(String vicinity) {
        mVicinity = vicinity;
    }

    /**
     * 周辺を返却する。
     *
     * @return 周辺
     */
    public String getVicinity() {
        return mVicinity;
    }

    /**
     * マーカーを設定する。
     *
     * @param marker マーカー
     */
    public void setMarker(Marker marker) {
        mMarker = marker;
    }

    /**
     * マーカーを返却する。
     *
     * @return マーカー
     */
    public Marker getMarker() {
        return mMarker;
    }
}
