package com.dasinwong.treelistview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dasinwong.treelistview.R;
import com.dasinwong.treelistview.bean.TreeNode;

import java.util.HashMap;
import java.util.List;

public class TreeAdapter extends BaseAdapter {

    private static final String ROOT_NODE_ID = "0";
    private static int padding;

    private Context mContext;
    private List<TreeNode> mTreeNodeList;
    private HashMap<String, TreeNode> mTreeNodeMap;

    public TreeAdapter(Context context, List<TreeNode> treeNodeList) {
        mContext = context;
        mTreeNodeList = treeNodeList;

        padding = (int) (20 * mContext.getResources().getDisplayMetrics().density + 0.5f);

        if (null == mTreeNodeMap)
            mTreeNodeMap = new HashMap<>();
        for (TreeNode node : mTreeNodeList)
            mTreeNodeMap.put(node.getNodeID(), node);
    }

    public void refresh(List<TreeNode> treeNodeList) {
        mTreeNodeList = treeNodeList;
        for (TreeNode node : mTreeNodeList)
            mTreeNodeMap.put(node.getNodeID(), node);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        int count = 0;
        for (TreeNode node : mTreeNodeList) {
            if (ROOT_NODE_ID.equals(node.getParentNodeID()))
                count++;
            else if (mTreeNodeMap.get(node.getParentNodeID()).isExpand())
                count++;
        }
        return count;
    }


    @Override
    public Object getItem(int position) {
        return mTreeNodeList.get(getVisibleNodeIndex(position));
    }

    /**
     * 获取可视节点的数据源下标
     */
    private int getVisibleNodeIndex(int position) {
        int count = 0;
        for (int i = 0; i < mTreeNodeList.size(); i++) {
            TreeNode node = mTreeNodeList.get(i);
            if (ROOT_NODE_ID.equals(node.getParentNodeID()))
                count++;
            else if (mTreeNodeMap.get(node.getParentNodeID()).isExpand())
                count++;
            if (position == (count - 1))
                return i;
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        NodeHolder holder;
        if (convertView == null) {
            holder = new NodeHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_node, null);
            holder.node_content = convertView.findViewById(R.id.node_content);
            convertView.setTag(holder);
        } else {
            holder = (NodeHolder) convertView.getTag();
        }
        final TreeNode node = (TreeNode) getItem(position);
        convertView.setPadding((node.getNodeLevel() + 1) * padding, 0, 0, 0);
        holder.node_content.setText(node.getNodeContent());
        return convertView;
    }

    public void setOnNodeClickListener(int position, OnNodeClickListener listener) {
        TreeNode node = (TreeNode) getItem(position);
        if (node.isLeaf()) {
            listener.onNodeClicked(node, getNodePath(node, new StringBuffer()));
        } else {
            if (node.isExpand())
                closeNode(node);
            else
                node.setExpand(true);
            notifyDataSetChanged();
        }
    }

    public interface OnNodeClickListener {
        void onNodeClicked(TreeNode node, String pathContent);
    }

    private String getNodePath(TreeNode node, StringBuffer buffer) {
        String nodeContent = node.getNodeContent();
        buffer.insert(0, nodeContent);
        if (node.getParentNodeID().equals(ROOT_NODE_ID)) {
            return buffer.toString();
        } else {
            buffer.insert(0, "-");
            getNodePath(mTreeNodeMap.get(node.getParentNodeID()), buffer);
        }
        return buffer.toString();
    }

    private void closeNode(TreeNode node) {
        node.setExpand(false);
        for (TreeNode treeNode : mTreeNodeList) {
            if (treeNode.getParentNodeID().equals(node.getNodeID()))
                closeNode(treeNode);
        }
    }

    private class NodeHolder {
        TextView node_content;
    }
}
