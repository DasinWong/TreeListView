package com.dasinwong.treelist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.dasinwong.treelistview.adapter.TreeAdapter;
import com.dasinwong.treelistview.bean.TreeNode;
import com.dasinwong.treelistview.view.TreeListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TreeListView mTreeListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTreeListView = findViewById(R.id.tree_list_view);

        List<TreeNode> treeNodeList = new ArrayList<>();
        treeNodeList.add(new TreeNode("1", "0", "第一个", 0));
        treeNodeList.add(new TreeNode("2", "1", "第二个", 0));
        treeNodeList.add(new TreeNode("3", "1", "第三个", 1));
        treeNodeList.add(new TreeNode("4", "3", "第四个", 0));
        treeNodeList.add(new TreeNode("5", "0", "第五个", 1));
        treeNodeList.add(new TreeNode("6", "5", "第六个", 0));
        Collections.shuffle(treeNodeList);

        mTreeListView.init(treeNodeList, new TreeAdapter.OnNodeClickListener() {
            @Override
            public void onNodeClicked(TreeNode node, String pathContent) {
                Toast.makeText(MainActivity.this, pathContent, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
