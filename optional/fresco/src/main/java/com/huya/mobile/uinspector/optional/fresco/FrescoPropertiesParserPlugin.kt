package com.huya.mobile.uinspector.optional.fresco

import android.util.Log
import android.view.View
import com.facebook.datasource.DataSource
import com.facebook.drawee.controller.AbstractDraweeController
import com.facebook.drawee.view.DraweeView
import com.facebook.imagepipeline.request.HasImageRequest
import com.huya.mobile.uinspector.impl.properties.view.ViewPropertiesParser
import com.huya.mobile.uinspector.impl.properties.view.ViewPropertiesParserPlugin

/**
 * @author YvesCheung
 * 2021/2/1
 */
open class FrescoPropertiesParserPlugin : ViewPropertiesParserPlugin {

    override val uniqueKey: String = "Fresco"

    override fun tryCreate(v: View): ViewPropertiesParser<out View>? {
        if (!noSuchMethod && v is DraweeView<*>) {
            val c = v.controller
            if (c is AbstractDraweeController<*, *>) {
                try {
                    val dataSource = getDataSourceMethod.invoke(c) as? DataSource<*>
                    if (dataSource is HasImageRequest) {
                        val request = dataSource.imageRequest
                        if (request != null) {
                            return DraweeViewPropertiesParser(v, request)
                        }
                    }
                } catch (e: NoSuchMethodException) {
                    Log.e("UInspector", e.toString())
                    noSuchMethod = true
                } catch (e: Throwable) {
                    Log.e("UInspector", e.toString())
                }
            }
        }
        return null
    }

    companion object {

        private var noSuchMethod = false

        private val getDataSourceMethod by lazy(LazyThreadSafetyMode.NONE) {
            val m = AbstractDraweeController::class.java.getDeclaredMethod("getDataSource")
            m.isAccessible = true
            m
        }
    }
}