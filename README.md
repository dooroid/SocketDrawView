<p align="center"><img src="http://i65.tinypic.com/kq0hv.png"></p>

SocketDrawView
=================

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)
[![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=15)
<br>
<!--[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-CircularImageView-lightgrey.svg?style=flat)](https://android-arsenal.com/details/1/2846)
[![Twitter](https://img.shields.io/badge/Twitter-@LopezMikhael-blue.svg?style=flat)](http://twitter.com/lopezmikhael)-->

This is an Android project allowing to use Socket, DrawView in the simplest way possible.

<img src="https://media.giphy.com/media/RJJL2I5lTmmYBwuNfM/giphy.gif" alt="sample" title="sample" align="center" />

USAGE
-----

To make a DrawView based on Socket add SocketDrawView in your layout XML and add SocketDrawView library in your project or you can also grab it via Gradle:

```groovy
implementation 'com.sk.broadband.socketdrawview:1.0.0'
```

XML
-----

#### Only Draw:
```xml
<com.sk.broadband.socketdrawview.DrawView
        android:id="@+id/draw_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
```

#### Client Socket with Draw:
```xml
<com.sk.broadband.socketdrawview.client.ClientSocketDrawView
        android:id="@+id/draw_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
```

#### Server Socket with Draw:
```xml
<com.sk.broadband.socketdrawview.server.ServerSocketDrawView
        android:id="@+id/draw_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
```

<!-- You must use the following properties in your XML to change your CircularImageView.


##### Properties:

* `app:civ_circle_color`        (color)     -> default WHITE
* `app:civ_border`              (boolean)   -> default true
* `app:civ_border_color`        (color)     -> default WHITE
* `app:civ_border_width`        (dimension) -> default 4dp
* `app:civ_shadow`              (boolean)   -> default false
* `app:civ_shadow_color`        (color)     -> default BLACK
* `app:civ_shadow_radius`       (float)     -> default 8.0f
* `app:civ_shadow_gravity`      (center, top, bottom, start or end) -> default bottom

:information_source: You can also use `android:elevation` instead of `app:civ_shadow` to have default Material Design elevation.
-->

KOTLIN
-----

#### Draw Usage:
```kotlin
override fun onTouchEvent(event: MotionEvent?): Boolean {
    drawView.draw(event!!)
    return super.onTouchEvent(event)
}
```

#### Draw Methods:
```kotlin
val drawView = findViewById<DrawView>(R.id.draw_view)
// Set Path color
drawView.pathColor = Color.rgb(red, green, blue)
// Set Path stroke width
drawView.pathStrokeWidth = seekBar.progress.toFloat()
// Set Path or Point
drawView.isPath = !drawView.isPath
drawView.undoPrev()
drawView.eraseAll()
```

#### Client Socket Usage:
```kotlin
override fun onResume() {
    super.onResume()
    clientSocketDrawView.connectSocket(ipAddress, 3000)
}

override fun onPause() {
    super.onPause()
    clientSocketDrawView.disconnectClient()
}

override fun onTouchEvent(event: MotionEvent?): Boolean {
    clientSocketDrawView.draw(event!!)
    clientSocketDrawView.drawServer(event)
    return super.onTouchEvent(event)
}
```

#### Client Methods:
```kotlin
clientSocketDrawView = findViewById<ClientSocketDrawView>(R.id.draw_view)
clientSocketDrawView.undoPrevServer()
clientSocketDrawView.eraseAllServer()
clientSocketDrawView.onClosedListener
clientSocketDrawView.onIOExceptionListener
clientSocketDrawView.onSocketTimeoutExceptionListener
clientSocketDrawView.onIllegalBlockingModeExceptionListener
clientSocketDrawView.onIllegalArgumentExceptionListener
```

#### Server Socket Usage:
```kotlin
override fun onResume() {
    super.onResume()
    serverSocketDrawView.createServerSocket()
}

override fun onPause() {
    super.onPause()
    serverSocketDrawView.disconnectServer()
}
```

#### Server Methods:
```kotlin
serverSocketDrawView = findViewById<ServerSocketDrawView>(R.id.draw_view)
serverSocketDrawView.getIPAddress()
serverSocketDrawView.onClosedListener
```

LIMITATIONS
-----

* It can't be used in case of private IP Address.

LINK
-----

**Github:**

I realized this project using this post:
* [refer to CircularImageView](https://github.com/lopspower/CircularImageView)
* [refer to FingerPaintView](https://github.com/PicnicSupermarket/FingerPaintView)


LICENCE
-----

SocketDrawView by Duhui is licensed under a [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0).
