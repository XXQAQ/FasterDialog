最新gradle依赖库请自行前往
https://www.jitpack.io/#XXQAQ/FasterDialog

        //以下所有Dialog均继承自BaseDialog，所以其子类可直接调用以下方法
        new BaseDialog(context) {}
                .setCustomView(R.layout.yourlayout) //设置你自己的布局方案，请保证你自己创建的layout文件中的关键控件Id名称与本框架指定控件Id一致即可
                .setStyle(STYLE_BASEDIALOG)         //设置Dialog的样式，自带有 STYLE_BASEDIALOG STYLE_TRANSLUCENTDIALOG STYLE_ALERTDIALOG三种款式，不同的Dialog默认样式不同
                .setAlpha(0.8f)         //Dialog透明度
                .setElevation(8.0f)     //Dialog卡片阴影
                .setAutoDismissTime(1000*5) //设置Dialog自动消失时间
                .setPopupFromScreen(Gravity.TOP|Gravity.BOTTOM|Gravity.LEFT|Gravity.RIGHT)               //设置Dialog从某个方向弹窗
                .setPopupFromView(attachView,Gravity.TOP|Gravity.BOTTOM|Gravity.LEFT|Gravity.RIGHT)      //设置Dialog从某个控件弹出，可以重载此函数设置弹出方向
                .setMaxHeight(100)          //最大高度(px)
                .setMaxHeightPercent(0.5f)  //最大高度占屏幕的百分比（0-1.0f）
                .setMaxWidth(100)           //最大宽度(px)
                .setMaxWidthPercent(0.5f)   //最大宽度占屏幕的百分比（0-1.0f）
                .setHeight(100)     //宽（px）
                .setHeightMatch()   //高满屏
                .setHeightWrap()    //高内容包裹
                .setHeightPercent(0.5f)     //g高占屏幕的百分百0-1.0f
                .setWidth(100)      //宽（px）
                .setWidthMatch()    //宽满屏
                .setWidthWrap()     //宽包裹内容
                .setWidthPercent(0.5f)      //宽占屏幕的百分百0-1.0f
                .setTag(null)
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                //以下三种均为监听器
                .addOnCancelListener(null)
                .addOnDismissListener(null)
                .addOnShowListener(null)
                .show();


        //CustomDialog用于高定制化界面，可以将自己构建的View直接放置到CustomDialog上
        View view = null;
        new CustomDialog(context)
                .setCustomView(view)
                .setDisconViewOnDismiss(true)   //当Dialog消失以后，是否需要与View断开容器联系,默认为true
                .show();


        //ListDialog用于列表类性的数据选择，注意这里的列表不仅是纯文字，也可以是图标，如常见的分享界面和附近推荐
s        //ListDialog继承与NormalDialog，可以使用NormalDialog所有API
        new ListDialog(context)
                .setCustomView(R.layout.layout_listdialog_xq,R.layout.item_nearbydialog)//设置Dialog布局与Item布局，以下四种为高定制化布局方案
                .setBottomLayoutStyle()  //Android标准底部弹窗
                .setXQLayoutStyle()      //APP常见风格样式
                .setMeterailLayoutStyle()//Meterail标准样式
                .setQQMenuLayoutStyle()  //QQ底部Menu样式弹窗
                .setItemList(new LinkedList<>())                //设置Item数据列表
                .setChooseMode(ListDialog.CHOOSEMODE_SINGLE)    //设置选择默认，有单选CHOOSEMODE_SINGLE 与多选CHOOSEMODE_MULTI 两种模式
                //单选模式方法
                .setSelection(null) //单选模式下设置已选择的数据
                .setOnItemSelectedListener(new BaseListDialog.OnItemSelectedListener() {    //单选模式下的监听
                    @Override
                    public void onItemSelected(BaseListDialog dialog, ItemBean bean) {

                    }
                })
                //多选模式方法
                .setSelectionList(new LinkedList<>())//多选模式下设置已选择的数据
                .setOnItemsSelectedListener(new BaseListDialog.OnItemsSelectedListener() {    //多选模式下的监听
                    @Override
                    public void onItemsSelected(BaseListDialog dialog, List<ItemBean> list) {

                    }
                })
                .show();


        //LoadingDialog
        new LoadingDialog(context)
                .setLoadingText("加载中")
                .show();

        //ProgressDialog (待完善)，继承于SimpleDialog.比较于LoadingDialog，ProgressDialog可以显示精确的进度条并且界面更加复杂
        new ProgressDialog(context)
                .setProgress(0.5f)
                .setAutoDismissTime(10*1000)    ///ProgressDialog设置自动消失以后，不需要外界控制,进度条会自动递增，你可以在业务逻辑完成以后setProgress(1.0f)再dismiss(),以达到常见的App加载效果
                .show();

        //EditDialog (待完善),继承于NormalDialog
        new EditDialog(context)
                //EditDialog天生支持任意数量个EditText，因此可以设置多个InputBean，每个InputBean都代表对应EditText的具体内容，切记如果不设置InputBean将会导致EditText无法显示
                .setInputBean0(new InputBean())
                .setInputBean1(new InputBean())
                .setInputBean(2,new InputBean())
                .setOnEditCompletedListener(new BaseEditDialog.OnEditCompletedListener() {
                    @Override
                    public void onEditCompleted(BaseEditDialog dialog, SparseArray<CharSequence> array) {
                        String str0 = array.get(0).toString();
                        String str1 = array.get(1).toString();
                        String str2 = array.get(2).toString();
                        //更多输入框按规律递加
                    }
                })
                .show();
                
        //全局弹窗，可以在Service Provider Receive或者其它任何无Context环境下调用，该方案无需系统弹窗权限申请
        DialogManager.showAnywhere(new DialogManager.DialogDelegateActivity.DialogContextProvider() {
            @Override
            public BaseDialog createDialog(Context context) {
                return new ProgressDialog(context).setTitle("手机正在被黑客攻击\n10秒后即将重启").setAutoDismissTime(10*1000);
            }
        });

PS:本框架还对PopupWindow进行了优化处理，目前测试版中可以使用以上API方式弹出PopupWindow，还有更多实际上已经写好但是没有文档的API调用，有问题的基友请在Issues中留言，我会在第一时间回复
