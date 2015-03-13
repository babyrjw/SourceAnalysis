package butterknife.internal;

import static butterknife.internal.ButterKnifeProcessor.VIEW_TYPE;

/**
 * 当出现一个@InjectView时，为InjectView生成一个ViewBinding
 * name 是@InjectView注解的字段名称
 * type 是@InjectView注解的字段类型
 */
final class ViewBinding implements Binding {
  private final String name;
  private final String type;
  private final boolean required;

  ViewBinding(String name, String type, boolean required) {
    this.name = name;
    this.type = type;
    this.required = required;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  @Override public String getDescription() {
    return "field '" + name + "'";
  }

  public boolean isRequired() {
    return required;
  }

  public boolean requiresCast() {
    return !VIEW_TYPE.equals(type);
  }
}
