package butterknife.internal;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 在其他的事件响应注解上附加基本信息
 * 例如
 * 原本代码是：
 * TextView textview ;
 * textview.setOnClickListener(new OnClickListener(){
 *     void OnClick(View view){
 *
 *     }
 * }
 *
 * 现在代码是：
 * @OnClick(R.id.textview)
 * public void OnTextViewClick(View view){
 *
 * }
 *
 * targetType: TextView
 * type:       OnClickListener
 * setter:     setOnClickListener
 *
 * method:     OnClick
 *
 * */
@Retention(RUNTIME) @Target(ANNOTATION_TYPE)
public @interface ListenerClass {
  String targetType();

  /** Name of the setter method on the {@link #targetType() target type} for the listener. */
  String setter();

  /** Fully-qualified class name of the listener type. */
  String type();

  /** The number of generic arguments for the type. This used used for casting the view. */
  int genericArguments() default 0;

  //由于有的Listener不止一个方法，于是这里使用callbacks来表示其中一个方法
  /** Enum which declares the listener callback methods. Mutually exclusive to {@link #method()}. */
  Class<? extends Enum<?>> callbacks() default NONE.class;

  /**
   * Method data for single-method listener callbacks. Mutually exclusive with {@link #callbacks()}
   * and an error to specify more than one value.
   */
  //对于只有一个方法的Listener使用method字段
  ListenerMethod[] method() default { };

  /** Default value for {@link #callbacks()}. */
  enum NONE { }
}
