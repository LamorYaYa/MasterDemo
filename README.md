## 自定义Dialog的创建
    ICustomDialog iCustomDialog = new ICustomDialog.Builder(this)
                // 设置布局
                .setLayoutResId(R.layout.test_dialog_layout)
                // 点击空白是否消失
                .setCanceledOnTouchOutside(false)
                // 点击返回键是否消失
                .setCancelable(false)
                // 设置Dialog的绝对位置
                .setDialogPosition(Gravity.CENTER)
                // 设置自定义动画
                .setAnimationResId(0)
                // 设置监听ID
                .setListenedItems(new int[]{R.id.btn_share})
                // 设置回掉
                .setOnDialogItemClickListener(new ICustomDialog.OnDialogItemClickListener() {
                    @Override
                    public void onDialogItemClick(ICustomDialog thisDialog, View clickView) {
                        thisDialog.dismiss();
                    }
                })
                .build();

        iCustomDialog.show();

## 默认Dialog创建
    new IDefaultDialog(this).builder()
               .setGravity(Gravity.CENTER)
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .setTitle("29 Nov.")
                .setSubTitle("The things you own end up owing you.")
                .setLeftButton("CANCEL", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "CANCEL", Toast.LENGTH_SHORT).show();
                    }
                })
                .setRightButton("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "OK", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();

## 网络请求,自定义泛型

    @FormUrlEncoded
    @POST("gginterface/api/sendVerifySMS.html")
    Observable<Beans> sendCode(@FieldMap() Map<String, String> params);

    Map<String, String> mHashMap = new HashMap<>();
    mHashMap.put("","");
    mHashMap.put("","");

    IHttpManager.doRequest(IRetrofit.createApi(HttpService.class).sendCode(mHashMap), new IHttpManager.IResponseListener<Beans>() {
        @Override
        public void onSuccess(Beans data) {
        }
        @Override
        public void onFail(Throwable e) {
        }
    });

## 网络请求,统一格式 返回

    @FormUrlEncoded
    @POST("gginterface/api/sendVerifySMS.html")
    Observable<HttpReseult<Beans>> sendCode(@FieldMap() Map<String, String> params);

    Map<String, String> mHashMap = new HashMap<>();
    mHashMap.put("","");
    mHashMap.put("","");

    IHttpManager.doRequestCommon(IRetrofit.createApi(HttpService.class).sendCode(mHashMap), new IHttpManager.IResponseListener<Beans>() {
         @Override
         public void onSuccess(Beans data) {
         }

         @Override
         public void onFail(Throwable e) {

         }
    });

## 权限使用
    ...


## 自定义Button，设置圆角 设置selector 点击颜色变化 share..
    xmlns:app="http://schemas.android.com/apk/res-auto"

    <com.master.app.view.ICustomButton
        android:layout_width="match_parent"
        android:layout_height="34dp"
        android:layout_marginLeft="16dp"
        android:layout_marginRight="16dp"
        android:gravity="center"
        android:text="哈哈哈"
        app:backColor="#234234"
        app:backColorPress="#b1dec9"
        app:backGroundImage="@mipmap/ic_launcher"
        app:backGroundImagePress="@mipmap/ic_launcher_round"
        app:isDrawable="false"
        app:radius="8"
        app:textColor="#000000"
        app:textColorPress="#ffffff" />