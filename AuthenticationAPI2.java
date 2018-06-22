JSONObject objAuthentication = xmlJSONObj.getJSONObject(Constant.DATA_ROOT);
        Iterator<String> iteAuthentication = objAuthentication.keys();
        while (iteAuthentication.hasNext()) {
            String key = iteAuthentication.next();

            if (Constant.RESULT.equals(key)) {
                JSONObject objResult = objAuthentication.getJSONObject(Constant.RESULT);
                // 処理結果コード
                atoMapReponse.put(Constant.CODE, objResult.get(Constant.CODE));
                // メッセージ
                atoMapReponse.put(Constant.MESSAGE, objResult.get(Constant.MESSAGE));

            } else if (Constant.APPL_DATA.equals(key)) {
                JSONObject objData = objAuthentication.getJSONObject(Constant.APPL_DATA);
                // Egov-利用者ID
                atoMapReponse.put(Constant.USER_ID, objData.get(Constant.USER_ID_EGOV));
                // アクセスキー
                atoMapReponse.put(Constant.ACCESS_KEY, objData.get(Constant.ACCESS_KEY));
                // 前回認証が成功した日時
                atoMapReponse.put(Constant.LAST_AUTHENTICATION_DATE, objData.get(Constant.LAST_AUTHENTICATION_DATE));
            }
        }
        // 返信データ作成処理
        // 作成した【レスポンスデータ】を、JSON形式に変換して、呼出元（通信ライブラリ）に返却する。
        // 「電子申請サービス_基底API」.”MAP_JSON変換”呼び出す。
        AtomicReference<String> atoJsonReponse = new AtomicReference<String>();
        result = super.convertMapToJson(atoMapReponse, atoJsonReponse);
        if (!result) {
            return result;
        }
        resultInfo.set(atoJsonReponse.get());
        result = true;
        return result;
    }

}
