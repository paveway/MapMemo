package info.paveway.mapmemo;

/**
 * マップメモ
 * 共通定数クラス
 *
 * @version 1.0 新規作成
 *
 */
public class CommonConstants {

    /**
     * URL定数
     *
     */
    public class Url {
        /** ベースURL */
        public static final String BASE = "https://maps.googleapis.com/maps/api/place/nearbysearch/json";
    }

    /**
     * パラメータキー定数
     *
     */
    public class ParamKey {
        /** URL */
        public static final String URL = "url";

        /** ステータス */
        public static final String STATUS = "status";

        /** 結果 */
        public static final String RESULTS = "results";

        /** 位置 */
        public static final String GEOMETRY = "geometry";

        /** ロケーション */
        public static final String LOCATION = "location";

        /** 緯度 */
        public static final String LAT = "lat";

        /** 経度 */
        public static final String LNG = "lng";

        /** アイコン */
        public static final String ICON = "icon";

        /** ID */
        public static final String ID = "id";

        /** 名前 */
        public static final String NAME = "name";

        /** プレースID */
        public static final String PLACE_ID = "place_id";

        /** リファレンス */
        public static final String REFERENCE = "reference";

        /** スコープ */
        public static final String SCOPE = "scope";

        /** タイプ */
        public static final String TYPES = "types";

        /** 周辺 */
        public static final String VICINITY = "vicinity";
    }

    /**
     * ローダーID定数
     *
     */
    public class LoaderId {
        /** プレイスID */
        public static final int PLACES = 1;
    }

    /**
     * エンコーディング定数
     *
     */
    public class Encoding {
        /** UTF-8 */
        public static final String UTF_8 = "UTF-8";
    }

    /**
     * 言語定数
     *
     */
    public class Language {
        /** 日本 */
        public static final String JA = "ja";
    }

    /**
     * プレースレスポンスステータス定数
     *
     */
    public class PlaceResponseStatus {
        /** OK */
        public static final String OK = "OK";

        /** 結果0件 */
        public static final String ZERO_RESULTS = "ZERO_RESULTS";

        /** 検索結果件数オーバー */
        public static final String OVER_QUERY_LIMIT = "OVER_QUERY_LIMIT";

        /** 許可されないリクエスト */
        public static final String REQUEST_DENIED = "REQUEST_DENIED";

        /** 無効なリクエスト */
        public static final String INVALID_REQUEST = "INVALID_REQUEST";
    }
}
