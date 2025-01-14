# Inspect custom view properties

Using Java SPI mechanism, You can intergrate your custom `view`/`LayoutParams` properties into Uinspector. Such as [Uinspecor-Fresco](https://github.com/YvesCheung/UInspector/blob/2.x/docs/uinspector-optional-fresco.md) / [Uinspector-Lottie](https://github.com/YvesCheung/UInspector/blob/2.x/docs/uinspector-optional-lottie.md).

1. Create a class implements the `ViewPropertiesPlugin`

    ```kotlin
    import com.pitaya.mobile.uinspector.impl.properties.view.ViewPropertiesParser
    import com.pitaya.mobile.uinspector.properties.view.ViewPropertiesPlugin

    class YourPlugin : ViewPropertiesPlugin {

        override fun tryCreate(v: View): ViewPropertiesParser<out View>? {
            if (v is YourCustomView) {
                return YourCustomViewParser(v)
            }
            //Just return null if you don't want to handle this view
            return null
        }
    }
    ```

2. Create a class extends the `ViewPropertiesParser`

    ```kotlin
    import android.view.View
    import com.pitaya.mobile.uinspector.impl.properties.view.ViewPropertiesParser
    import com.yy.mobile.whisper.Output

    class YourCustomViewParser(view: YourCustomView) : ViewPropertiesParser<YourCustomView>(view) {

        override fun parse(@Output props: MutableMap<String, Any?>) {
            super.parse(props)
            props["key"] = value
        }
    }
    ```

4. Register your plugin to `UInspectorPluginService`

    ```kotlin
    import com.pitaya.mobile.uinspector.plugins.UInspectorPluginService
    import com.pitaya.mobile.uinspector.plugins.UInspectorPlugins
    import com.pitaya.mobile.uinspector.properties.view.ViewPropertiesPlugin

    class YourPluginService : UInspectorPluginService {

        override fun onCreate(context: Context, plugins: UInspectorPlugins) {
            plugins.prepend(ViewPropertiesPlugin::class.java, YourPlugin())
        }
    }
    ```

4. Create a file named `com.pitaya.mobile.uinspector.plugins.UInspectorPluginService` in the directory `src/main/resources/META-INF/services`. Write down your service class name `YourPluginService` in the file.

    [See the demo: /src/main/resources/META-INF/services/](https://github.com/YvesCheung/UInspector/blob/2.x/optional/glide/src/main/resources/META-INF/services/com.pitaya.mobile.uinspector.plugins.UInspectorPluginService)

5. OK! Now run your app and click the `YourCustomView` on screen.