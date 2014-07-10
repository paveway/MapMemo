package info.paveway.mapmemo.data;

import java.io.Serializable;

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

    /**
     * IDを設定する。
     *
     * @param id ID
     */
    public void setId(String id) {
        mId = id;
    }

    public String getId() {
        return mId;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setPlaceId(String placeId) {
        mPlaceId = placeId;
    }

    public String getPlaceId() {
        return mPlaceId;
    }

    public void setLat(double lat) {
        mLat = lat;
    }

    public double getLat() {
        return mLat;
    }

    public void setLng(double lng) {
        mLng = lng;
    }

    public double getLng() {
        return mLng;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setReference(String reference) {
        mReference = reference;
    }

    public String getReference() {
        return mReference;
    }

    public void setScope(String scope) {
        mScope = scope;
    }

    public String getScope() {
        return mScope;
    }

    public void setTypes(String types) {
        mTypes = types;
    }

    public String getTypes() {
        return mTypes;
    }

    public void setVicinity(String vicinity) {
        mVicinity = vicinity;
    }

    public String getVicinity() {
        return mVicinity;
    }
}
