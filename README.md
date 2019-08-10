最新gradle依赖库请自行前往
https://www.jitpack.io/#XXQAQ/FasterDialog

PS:注意注意注意：最新版FasterDialog使用了接口默认实现技术，所以依赖于jdk1.8及以上版本。如果要使用FasterDialog，请将自己的Studio工程设置为1.8！

        //以下所有Dialog均继承自BaseDialog，所有子类可直接调用以下方法
        new BaseDialog(context) {}
                //设置你自己的布局方案，请保证你自己创建的layout文件中的关键控件Id名称与本框架指定控件Id一致
                .setCustomView(R.layout.yourlayout)
                //设置Dialog的样式，默认有 STYLE_BASE  STYLE_TRANSLUCENT  STYLE_ALERT  三种
                .setStyle(STYLE_BASE)
                //Dialog透明度
                .setAlpha(0.8f)
                //Dialog卡片阴影
                .setElevation(8.0f)
                //设置Dialog自动消失时间
                .setAutoDismissTime(1000*5)
                //设置Dialog从某个方向弹窗
                .setPopupFromScreen(Gravity.TOP|Gravity.BOTTOM|Gravity.LEFT|Gravity.RIGHT)
                //设置Dialog从attachView下方弹出
                .setPopupFromView(attachView)
                //设置Dialog从attachView的某个方向弹出
                .setPopupFromView(attachView,Gravity.TOP|Gravity.BOTTOM|Gravity.LEFT|Gravity.RIGHT)
                //从attachView上某点击的位置弹出，参照window任意界面右键菜单效果
                .setPopupFromViewTouchLocation(attachView)
                //最大高度(px)
                .setMaxHeight(100)
                //最大高度占屏幕的百分比（0-1.0f）
                .setMaxHeightPercent(0.5f)
                //最大宽度(px)
                .setMaxWidth(100)
                //最大宽度占屏幕的百分比（0-1.0f）
                .setMaxWidthPercent(0.5f)
                //宽（px）
                .setHeight(100)
                //高满屏
                .setHeightMatch()
                //高内容包裹
                .setHeightWrap()
                //g高占屏幕的百分百0-1.0f
                .setHeightPercent(0.5f)
                //宽（px）
                .setWidth(100)
                //宽满屏
                .setWidthMatch()
                //宽包裹内容
                .setWidthWrap()
                //宽占屏幕的百分百0-1.0f
                .setWidthPercent(0.5f)
                //TAG
                .setTag(null)
                //是否可取消
                .setCancelable(false)
                //是否可点击外部取消
                .setCanceledOnTouchOutside(false)
                //以下三种均为监听器
                .addOnCancelListener(dialog -> {})
                .addOnDismissListener(dialog -> {})
                .addOnShowListener(dialog -> {})
                //显示
                .show();

        //CustomDialog用于高定制化界面，可以将自己构建的任意View直接展示到Dialog上
        new CustomDialog(context)
                //设置需要展示的View
                .setCustomView(view)
                //当Dialog消失以后，是否需要与传入的View断开联系,默认为true
                .setDisconViewOnDismiss(true)
                .show();

        //NormalDialog的使用场景与API均可参照AlertDialog，理论上NormalDialog可以兼容所有App弹窗方案
        //NormalDialog继承于SimpleDialog
        new NormalDialog(context)
                //设置你自己的布局方案，以下为高定制化布局
                .setCustomView(R.layout.yourlayout)
                //Meterail布局样式
                .setMeterailLayoutStyle()
                //标准自定义样式
                .setXQLayoutStyle()
                //大图样式，参照常见广告弹窗
                .setBigImageLayoutStyle()
                //设置图标(Res文件资源)
                .setImageRes(R.mipmap.icon)
                //设置图标(网络资源)
                .setImageUrl("http://...")
                //设置标题
                .setTitle("我是大标题")
                //设置内容
                .setContent("我是内容")
                //设置确定 文字+监听
                .setPositiveText(NormalDialog.CONFIRM)
                .setPositiveListener(new BaseDialog.OnDialogClickListener() {
                    @Override
                    public void onClick(BaseDialog dialog) {

                    }
                })
                //设置取消 文字+监听
                .setNegativeText(NormalDialog.CANCEL)
                .setNegativeListener(new BaseDialog.OnDialogClickListener() {
                    @Override
                    public void onClick(BaseDialog dialog) {

                    }
                })
                //设置中立 文字+监听
                .setNeutralText("随便选")
                .setNeutralListener(new BaseDialog.OnDialogClickListener() {
                    @Override
                    public void onClick(BaseDialog dialog) {

                    }
                })
                .show();

        List<ItemBean> list = new LinkedList<>();
        for (int i=0;i<=0;i++) list.add(new ItemBean("子标题" + i));
        //ListDialog用于列表类性数据选择，注意这里的列表不仅是纯文字列表，也可以是图标列表，并且列表本身也不局限于ListView样式，也可以是GridView样式，如常见的分享界面
        //ListDialog继承与NormalDialog，可以使用NormalDialog所有功能
        new ListDialog(context)
                //设置Dialog布局与Item布局，以下为高定制化布局
                .setCustomView(R.layout.yourLayout,R.layout.itemLayout)
                //Meterail标准样式
                .setMeterailLayoutStyle()
                //Android标准底部弹窗样式
                .setBottomLayoutStyle()
                //标准自定义样式
                .setXQLayoutStyle()
                //参照QQ菜单Menu样式
                .setMenuLayoutStyle()
                //PopupMenu样式
                .setPopupMenuLayoutStyle()
                //设置Item数据列表（这里其实并不限制具体数据类型，只要你提供的对象实现了ItemBehavior接口，那么ListDialog就能识别你的数据源）
                .setItemList(list)
                //设置Item数据列表并追加
                .setItemList(list,true)
                //Item选择回调方法
                .setOnItemSelectedListener(new BaseListDialog.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(BaseListDialog dialog, ItemBehavior bean) {

                    }
                })
                //设置选择模式，有  CHOOSE_MODE_SINGLE  与  CHOOSE_MODE_MULTI  两种模式
                .setChooseMode(ListDialog.CHOOSE_MODE_SINGLE)
                //设置已选择的数据
                .setSelection(list.get(0))
                //设置已选择的多个数据
                .setSelectionList(list)
                //特殊的Item选择回调方法，因为有些APP的设计是：选择Item的时候不立即回调，在点击确认键以后再将前面点击的结果进行回调，如果你没有这个需求可以无视该方法
                .setPositiveListener(new BaseListDialog.OnItemCompletedListener() {
                    @Override
                    public void onItemCompleted(BaseListDialog dialog, List<ItemBehavior> list) {

                    }
                })
                .show();


        //LoadingDialog ...
        new LoadingDialog(context)
                //设置你自己的布局方案
                .setCustomView(R.layout.yourlayout)
                //设置加载中 文字提示
                .setLoadingText("加载中")
                .show();

        //相比于LoadingDialog，ProgressDialog可以展示更精确的进度值
        //ProgressDialog继承于SimpleDialog
        new ProgressDialog(context)
                //设置你自己的布局方案，以下为高定制化布局
                .setCustomView(R.layout.yourlayout)
                //圆形Meterail布局样式
                .setCircleMeterailLayoutStyle()
                //圆形标准自定义样式
                .setCircleXQLayoutStyle()
                //水平Meterail布局样式
                .setHorizontalMeterailLayoutStyle()
                //水平标准自定义样式
                .setHorizontalXQLayoutStyle()
                //设置进度（0—1.0f）
                .setProgress(0.5f)
                //ProgressDialog设置自动消失以后，不需要外界控制,进度条会自动递增，你可以在业务逻辑完成以后手动调用setProgress(1.0f)再dismiss(),以达到常见的App加载效果
                .setAutoDismissTime(10*1000)
                .setTitle("加载中...")
                .show();

        //EditDialog用于弹框输入场景
        //EditDialog继承于NormalDialog
        new EditDialog(context)
                //设置你自己的布局方案，以下为高定制化布局
                .setCustomView(R.layout.yourlayout)
                //Meterail布局样式
                .setMeterailLayoutStyle()
                //标准自定义样式
                .setXQLayoutStyle()
                //EditDialog天生支持任意数量个EditText，因此可以设置多个InputBean，每个InputBean都代表对应EditText的具体内容，切记如果不设置InputBean将会导致对应的EditText无法显示
                .setInputBean0(new InputBean("请输入昵称","张三"))
                .setInputBean1(new InputBean("请输入密码"))
                .setInputBean(2,new InputBean("验证码",null,null,10, InputType.TYPE_CLASS_NUMBER))
                //输入完成回调
                .setPositiveListener(new BaseEditDialog.OnEditCompletedListener() {
                    @Override
                    public void onEditCompleted(BaseEditDialog dialog, SparseArray<CharSequence> array) {
                        String str0 = array.get(0).toString();
                        String str1 = array.get(1).toString();
                        String str2 = array.get(2).toString();
                        //更多输入框按规律递加
                    }
                })
                .setTitle("请输入...")
                .show();

        //全局弹窗，可以在Service Provider Receive或者其它任何无Context环境下调用，该方案无需系统弹窗权限申请
        DialogManager.showAnywhere(new DialogManager.DialogDelegateActivity.DialogContextProvider() {
            @Override
            public BaseDialog createDialog(Context context) {
                //这里Dialog只需要new出来即可，千万不要手贱去主动show！！！
                return new ProgressDialog(context).setTitle("手机正在被黑客攻击\n10秒后即将重启").setAutoDismissTime(10*1000);
            }
        });

        //目前正在将Dialog与PopupWindow做到无差异化封装，BasePopupWindow还需要后续调整，有需要用到的话请与本人联系
        BasePopupWindow basePopupWindow;
