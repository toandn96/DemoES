package jp.co.melb.pp.es.api;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.codehaus.jackson.JsonParseException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.co.melb.pp.es.api.common.BaseAPI;
import jp.co.melb.pp.es.common.Constant;
import jp.co.melb.pp.es.common.FunctionUtil;
import jp.co.melb.pp.es.common.MessageUtil;
import jp.co.melb.pp.es.common.ParameterUtil;
import jp.co.melb.pp.es.common.log.AccessLogWriter;
import jp.co.melb.pp.es.common.log.ApplicationProcessingLogWriter;
/**
 * 利用者認証API: 利用者認証サービスでは、電子申請ツール等のクライアントからの要求を受けて、利用者認証を行う.
 * <p>
 * また、認証結果およびアクセスキーをクライアントへ通知する。
 * </p>
 * <p>
 * e-Govシステムでは、登録済みの利用者IDであること、証明書の識別情報と利用者IDの対応関係の確認が行われる。
 * </p>
 * <p>
 * 利用する外部連携APIは「利用者認証」である。
 * </p>
 * 
 * @author van-nttc
 * @version 1.0.0
 *
 */
@RestController
public class AuthenticationAPI extends BaseAPI {

    /**
     * 利用者認証: 登録済みのユーザ（利用者ID）であるか利用者認証をする.
     * <p>
     * 電子申請による申請や発行済み公文書などの各種電子申請処理を行う前に実行する。
     * </p>
     * 
     * <pre>
     * <code>本APIの処理を以下のように実行する：
     *処理手順１([リクエスト].”処理区分”＝１（申請データ作成）の場合)
     *    利用者認証データ作成処理。
     *    利用者認証送信パラメーターを取得する。 戻り値：[認証用HTTPボディ]の文字列。
     *    [認証用HTTPボディ]の暗号化を処理する。
     *    作成した【レスポンスデータ】を、JSON形式に変換して、呼出元（通信ライブラリ）に返却する。
     *    次を含む通信ライブラリを返すレスポンスパラメータを作成する。
     *        [利用者ID]、 [証明書](送信データ形式)、 [認証用HTTPボディ](送信データ形式)
     *処理手順２([リクエスト].”処理区分”＝２（署名データ送信）の場合)
     *    [リクエスト].”認証用HTTPボディ”の復号化を処理する。
     *    「電子申請サービス_基底API」.”送受信処理”呼び出す。応答結果を結果情報として、呼び出し元に返す。
     *    利用者認証結果作成: 作成した【レスポンスデータ】を、JSON形式に変換して、呼出元（通信ライブラリ）に返却する。
     *    次を含む通信ライブラリを返すレスポンスパラメータを作成する。
     *        [処理結果コード]、[メッセージ]、 [利用者ID]、[アクセスキー]、 [前回成功した認証日時]
     * </code>
     * </pre>
     * 
     * @author thuan-hd
     * @param param     パラメーター
     * <ul>
     * <li>loginId ログインID</li>
     * <li>userId 利用者ID</li>
     * <li>certificate 証明書</li>
     * <li>processKbn 処理区分</li>
     * <ul>
     * <li>1:申請データ作成</li>
     * <li>2:署名データ送信</li>
     * </ul>
     * <li>httpBodyLogin 認証用HTTPボディ</li>
     * <li>httpBodyApply 申請用HTTPボディ</li>
     * <li>procedureParams 手続別パラメーター</li>
     * </ul>
     * @return resultInfo   結果情報
     * @throws Exception    例外
     * <p>httpBody = emptyの場合</p>
     * <p>httpBodyの暗号化でfalseが返される場合</p>
     * <p>JSON文字列をMAPに変換してfalseが返される場合</p>
     * <p>外部連携API管理定義を取得し例外が発生する場合 </p>
     * <p>「電子申請サービス_基底API」.”送受信処理”呼び出す。 実行に成功した場合はfalse</p>
     * <p>”利用者認証結果作成”呼び出す。 実行に成功した場合はfalse</p>
     * @see RestController
     * @see RequestMapping
     * @see RequestParam
     */
    @RequestMapping("/authentication/login")
    public String authenticateUser(@RequestBody String body, 
            @RequestParam(value = Constant.PARAM, defaultValue = Constant.EMPTY_STRING) String param) throws Exception {

    	// paramだと大きいサイズのデータが受けれないためbodyでも受ける事を可能にする
    	// セキュリティ検査はparamで受ける
    	if ("".equals(param)) {
    		param = body;
    	}

    	return authenticateUser(param, true);
    }

    /**
     * 利用者認証: 登録済みのユーザ（利用者ID）であるか利用者認証をする.
     * <p>
     * 電子申請による申請や発行済み公文書などの各種電子申請処理を行う前に実行する。
     * </p>
     * 
     * @param param パラメーター
     * @return 認証結果
     * @throws Exception
     */
