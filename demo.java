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
