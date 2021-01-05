package com.huya.mobile.uinspector.impl.properties.view

import android.os.Build
import android.view.Gravity
import android.widget.RelativeLayout
import com.huya.mobile.uinspector.util.gravityToString

/**
 * @author YvesCheung
 * 2021/1/4
 */
open class RelativeLayoutPropertiesParser(view: RelativeLayout) : ViewPropertiesParser<RelativeLayout>(view) {

    override fun parse(props: MutableMap<String, Any?>) {
        super.parse(props)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (view.gravity != Gravity.START or Gravity.TOP) {
                props["gravity"] = gravityToString(view.gravity)
            }
        }
    }
}