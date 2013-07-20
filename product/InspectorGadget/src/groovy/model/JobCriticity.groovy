package model

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
enum JobCriticity {
    LOW(1,"Low"),
    MEDIUM(2, "Medium"),
    HIGH(3,"High"),
    CRITICAL(4, "Critical")

    public Integer id
    public String value

    public JobCriticity(Integer id, String value) {
      this.id = id
      this.value = value
    }

    public static List<String> list() {
        return Arrays.asList(LOW.value, MEDIUM.value, HIGH.value, CRITICAL.value);
    }
}
