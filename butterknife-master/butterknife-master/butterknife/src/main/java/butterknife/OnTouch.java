package butterknife;

import android.view.View;
import butterknife.internal.ListenerClass;
import butterknife.internal.ListenerMethod;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static android.view.View.OnTouchListener;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Bind a method to an {@link OnTouchListener OnTouchListener} on the view for each ID specified.
 * <pre><code>
 * {@literal @}OnTouch(R.id.example) boolean onTouch() {
 *   Toast.makeText(this, "Touched!", LENGTH_SHORT).show();
 *   return false;
 * }
 * </code></pre>
 * Any number of parameters from
 * {@link OnTouchListener#onTouch(android.view.View, android.view.MotionEvent) onTouch} may be used
 * on the method.
 *
 * @see OnTouchListener
 * @see Optional
 */
@Target(METHOD)
@Retention(CLASS)
@ListenerClass(
    targetType = "android.view.View",
    setter = "setOnTouchListener",
    type = "android.view.View.OnTouchListener",
        //这里将注解实例化后作为另外一个注解字段的值
    method = @ListenerMethod(
        name = "onTouch",
        parameters = {
            "android.view.View",
            "android.view.MotionEvent"
        },
        returnType = "boolean",
        defaultReturn = "false"
    )
)
public @interface OnTouch {
  /** View IDs to which the method will be bound. */
  int[] value() default { View.NO_ID };
}
