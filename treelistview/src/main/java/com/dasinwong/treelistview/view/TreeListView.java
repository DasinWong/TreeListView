package com.dasinwong.treelistview.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.dasinwong.treelistview.R;
import com.dasinwong.treelistview.adapter.TreeAdapter;
import com.dasinwong.treelistview.bean.TreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class TreeListView extends RelativeLayout {

    private static final String ROOT_NODE_ID = "0";

    private MeasureListView mListView;
    private List<TreeNode> mTreeNodeList;
    private HashMap<String, TreeNode> mTreeNodeMap;
    private TreeAdapter mTreeAdapter;

    public TreeListView(Context context) {
        this(context, null);
    }

    public TreeListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.merge_tree_list_view, this, true);
        initData(context);
        initView();
        afterInit();
    }

    private void initData(Context context) {
        if (null == mTreeNodeList)
            mTreeNodeList = new ArrayList<>();
        if (null == mTreeAdapter)
            mTreeAdapter = new TreeAdapter(context, mTreeNodeList);
        if (null == mTreeNodeMap)
            mTreeNodeMap = new HashMap<>();
    }

    private void initView() {
        mListView = findViewById(R.id.measure_list_view);
    }

    private void afterInit() {
        mListView.setAdapter(mTreeAdapter);
    }

    /**
     * 初始化数据与监听
     */
    public void init(@Nullable List<TreeNode> treeNodeList, final TreeAdapter.OnNodeClickListener listener) {
        if (null == treeNodeList) {
            mTreeNodeList.clear();
            mTreeAdapter.refresh(mTreeNodeList);
            return;
        }
        mTreeNodeList.clear();
        mTreeNodeList.addAll(formatList(treeNodeList));
        mTreeAdapter.refresh(mTreeNodeList);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mTreeAdapter.setOnNodeClickListener(position, listener);
            }
        });
    }

    /**
     * 格式化树型数据（设置排序，等级，叶子节点，是否展开）
     */
    private List<TreeNode> formatList(final List<TreeNode> treeNodeList) {

        for (TreeNode node : treeNodeList) {
            mTreeNodeMap.put(node.getNodeID(), node);
        }

        for (TreeNode node : treeNodeList) {
            node.setNodeLevel(calNodeLevel(node));
            node.setLeaf(!hasChildNode(node, treeNodeList));
            node.setExpand(false);
        }

        Collections.sort(treeNodeList, new Comparator<TreeNode>() {
            @Override
            public int compare(TreeNode node_1, TreeNode node_2) {
                int level_1 = node_1.getNodeLevel();
                int level_2 = node_2.getNodeLevel();
                if (level_1 == level_2) {
                    if (node_1.getParentNodeID().equals(node_2.getParentNodeID())) {
                        return node_1.getNodeOrder() > node_2.getNodeOrder() ? 1 : -1;
                    } else {
                        TreeNode node_1_parent = mTreeNodeMap.get(node_1.getParentNodeID());
                        TreeNode node_2_parent = mTreeNodeMap.get(node_2.getParentNodeID());
                        return compare(node_1_parent, node_2_parent);
                    }
                } else {
                    if (level_1 > level_2) {
                        if (node_1.getParentNodeID().equals(node_2.getNodeID())) {
                            return 1;
                        } else {
                            TreeNode node_1_parent = mTreeNodeMap.get(node_1.getParentNodeID());
                            return compare(node_1_parent, node_2);
                        }
                    } else {
                        if (node_2.getParentNodeID().equals(node_1.getNodeID())) {
                            return -1;
                        }
                        TreeNode node_2_parent = mTreeNodeMap.get(node_2.getParentNodeID());
                        return compare(node_1, node_2_parent);
                    }
                }
            }
        });
        return treeNodeList;
    }

    /**
     * 计算节点级数
     */
    private int calNodeLevel(@Nullable TreeNode node) {
        if (null == node || ROOT_NODE_ID.equals(node.getParentNodeID()))
            return 0;
        else
            return 1 + calNodeLevel(mTreeNodeMap.get(node.getParentNodeID()));
    }

    /**
     * 计算是否有子节点
     */
    private boolean hasChildNode(TreeNode treeNode, List<TreeNode> treeNodeList) {
        for (TreeNode node : treeNodeList) {
            if (treeNode.getNodeID().equals(node.getParentNodeID()))
                return true;
        }
        return false;
    }
}
