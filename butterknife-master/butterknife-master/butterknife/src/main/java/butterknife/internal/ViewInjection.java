package butterknife.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * 一个View 注入
 * 例如
 * @Injectiew(R.id.textview)
 * TextView textview;
 *
 *
 * @OnClick(R.id.textview)
 * public void OnTextViewClick(View view){
 *
 * }
 *
 * id:              R.id.textview
 * viewBinding:     为textview生成的ViewBinding，一个id可能需要注入给多个字段
 * listenerBindings：为textview生成的所有ListenerBinding，1个view可能有多个事件监听器，一个事件监听器
 *                  可能有多个事件响应函数
 */
final class ViewInjection {
  private final int id;
  private final Set<ViewBinding> viewBindings = new LinkedHashSet<ViewBinding>();
  private final LinkedHashMap<ListenerClass, Map<ListenerMethod, Set<ListenerBinding>>>
      listenerBindings = new LinkedHashMap<ListenerClass,
      Map<ListenerMethod, Set<ListenerBinding>>>();

  ViewInjection(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public Collection<ViewBinding> getViewBindings() {
    return viewBindings;
  }

  public Map<ListenerClass, Map<ListenerMethod, Set<ListenerBinding>>> getListenerBindings() {
    return listenerBindings;
  }

  public boolean hasListenerBinding(ListenerClass listener, ListenerMethod method) {
    Map<ListenerMethod, Set<ListenerBinding>> methods = listenerBindings.get(listener);
    return methods != null && methods.containsKey(method);
  }

  public void addListenerBinding(ListenerClass listener, ListenerMethod method,
      ListenerBinding binding) {
    Map<ListenerMethod, Set<ListenerBinding>> methods = listenerBindings.get(listener);
    Set<ListenerBinding> set = null;
    if (methods == null) {
      methods = new LinkedHashMap<ListenerMethod, Set<ListenerBinding>>();
      listenerBindings.put(listener, methods);
    } else {
      set = methods.get(method);
    }
    if (set == null) {
      set = new LinkedHashSet<ListenerBinding>();
      methods.put(method, set);
    }
    set.add(binding);
  }

  public void addViewBinding(ViewBinding viewBinding) {
    viewBindings.add(viewBinding);
  }

  public List<Binding> getRequiredBindings() {
    List<Binding> requiredBindings = new ArrayList<Binding>();
    for (ViewBinding viewBinding : viewBindings) {
      if (viewBinding.isRequired()) {
        requiredBindings.add(viewBinding);
      }
    }
    for (Map<ListenerMethod, Set<ListenerBinding>> methodBinding : listenerBindings.values()) {
      for (Set<ListenerBinding> set : methodBinding.values()) {
        for (ListenerBinding binding : set) {
          if (binding.isRequired()) {
            requiredBindings.add(binding);
          }
        }
      }
    }
    return requiredBindings;
  }
}
