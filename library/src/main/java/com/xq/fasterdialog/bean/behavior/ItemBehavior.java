package com.xq.fasterdialog.bean.behavior;

import com.xq.worldbean.bean.behavior.ImageBehavior;
import com.xq.worldbean.bean.behavior.PositionBehavior;
import com.xq.worldbean.bean.behavior.SwitchStateBehavior;
import com.xq.worldbean.bean.behavior.TitleBehavior;

public interface ItemBehavior<T extends ItemBehavior> extends TitleBehavior<T>, ImageBehavior<T>, PositionBehavior<T>, SwitchStateBehavior<T> {

    @Override
    default int getPosition() {
        return 0;
    }

    @Override
    default int getState() {
        return 0;
    }

}
