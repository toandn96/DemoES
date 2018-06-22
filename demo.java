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
