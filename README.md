**该项目是一个带有数字监听的EditText,字数达到了一定的情况下就会输入不进去的效果。**

先来看效果图吧:

![simple.gif](https://github.com/1002326270xc/NumberEditText/blob/master/photo/demo.gif)

##思路:
   其实从效果图上面看，这里控件的布局非常醒目的。整体是一个相对布局，相对布局里面又可以分为两块:editText+右下带数字的自定义view，大家可以看到这个输入框不会把下面数字监听的view给覆盖掉，是因为editText下面留出了这个自定义view的高度，所以到了最后一行的时候也不会把带数字的view给覆盖掉，就是这么简单，小伙伴们赶快行动起来吧。

##用法:
  <pre><code>
  ```<declare-styleable name="DynamicInputView">    
      <attr name="max_number" format="integer" />    
      <attr name="hint_text" format="string|reference" />    
      <attr name="text_size" format="dimension" />    
      <attr name="text_color" format="color" />
  </declare-styleable>```
  </code></pre>

从几个自定义的属性可以看出来怎么用了吧。

**max_number:** 限制最大的字数

**hint_text:** 初始化显示的字

**text_size:** 字的大小

**text_color:** 字的颜色

前期就定义这几个属性吧，如果大家觉得还有需要什么属性，可以反馈给我。控件也会不断地更新，尽量满足大家的需求。

##关于我:
   - email: a1002326270@163.com
   - 简书: http://www.jianshu.com/users/7b186b7247c1/latest_articles
