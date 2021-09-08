package org.codeformatter.utils;

import lombok.RequiredArgsConstructor;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.SequenceNode;

@RequiredArgsConstructor
public class YamlListConstructor<T> extends Constructor {

    private final Class<T> type;

    @Override
    protected Object constructObject(Node node) {

        if (node instanceof SequenceNode && isRootNode(node)) {
            ((SequenceNode) node).setListType(type);
        }

        return super.constructObject(node);
    }

    private boolean isRootNode(Node node) {

        return node.getStartMark().getIndex() == 0;
    }

}
