# TreeListView
自定义多层级树型列表
# 演示图
还没录
# 如何接入
Project层级下的build.gradle文件
```
allprojects {
    repositories {
    ...
    maven { url 'https://jitpack.io' }
    }
}
```
Module层级下的build.gradle文件
```
implementation 'com.github.DasinWong:TreeListView:1.0'
```
# 如何使用
布局中添加控件
```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.dasinwong.treelistview.view.TreeListView
        android:id="@+id/tree_list_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</RelativeLayout>
```
创建TreeNode数据集合，调用init方法
```
mTreeListView.init(treeNodeList, new TreeAdapter.OnNodeClickListener() {
    @Override
    public void onNodeClicked(TreeNode node, String pathContent) {
        Toast.makeText(MainActivity.this, pathContent, Toast.LENGTH_SHORT).show();
   }
});
```
