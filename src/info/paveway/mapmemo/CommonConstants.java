package info.paveway.mapmemo;

/**
 * 共通定数クラス
 *
 * @version 1.0 新規作成
 *
 */
public class CommonConstants {

    public static final String PLACES_API_KEY = "AIzaSyBfJZ3gRs_WKPWDCr11oCYe_U9hqVZKlMc";

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
        /** URLキー */
        public static final String URL = "url";
        public static final String RESULTS = "results";
        public static final String GEOMETRY = "geometry";
        public static final String LOCATION = "location";
        public static final String LAT = "lat";
        public static final String LNG = "lng";
        public static final String ICON = "icon";
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String PLACE_ID = "place_id";
        public static final String REFERENCE = "reference";
        public static final String SCOPE = "scope";
        public static final String TYPES = "types";
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
     * Extraキー定数
     *
     */
    public class ExtraKey {
        /** ユーザデータ */
        public static final String USER_DATA = "userData";

        /** ルームデータ */
        public static final String ROOM_DATA = "roomData";

        /** 進捗ダイアログタイトル */
        public static final String PROGRESS_TITLE = "progressTitle";

        /** 進捗ダイアログメッセージ */
        public static final String PROGRESS_MESSAGE = "progressMessage";

        /** ユーザ緯度 */
        public static final String USER_LATITUDE = "userLatutide";

        /** ユーザ経度 */
        public static final String USER_LONGITUDE = "userLongitude";

        /** メモ緯度 */
        public static final String MEMO_LATITUDE = "memoLatitude";

        /** メモ経度 */
        public static final String MEMO_LONGITUDE = "memoLongitude";
    }

    /**
     * エンコーディング
     *
     */
    public class Encoding {
        /** UTF-8 */
        public static final String UTF_8 = "UTF-8";
    }

    /**
     * 要求コード定数
     *
     */
    public class RequestCode {
        /** 設定画面 */
        public static final int SETTINGS = 1;

        /** 接続エラー解決要求 */
        public static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    }

    public class Action {
        public static final String ACTION_LOCATION = "info.paveway.hereclient.ACTION_LOCATION";

        public static final String ACTION_LOCATION_FAILED = "info.paveway.hereclient.ACTION_LOCATION_FAILED";
    }

    public class MemoRangeValue {
        public static final int SELF = 1;
        public static final int MEMBER = 2;
        public static final int ALL = 3;
    }
}
