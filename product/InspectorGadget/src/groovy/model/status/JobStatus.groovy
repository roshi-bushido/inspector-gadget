package model.status
/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
enum JobStatus {
    PENDING(1,"Pending"),
    OK(2, "Ok"),
    WARNING(3,"Warning"),
    ERROR(4, "Error")

    public Integer id
    public String value

    public JobStatus(Integer id, String value) {
      this.id = id
      this.value = value
    }

    public static List<String> valueList() {
        return Arrays.asList(PENDING.value, OK.value, WARNING.value, ERROR.value);
    }

    public static Boolean isValid(String name) {
        return JobStatus.valueList().any { x -> x.equalsIgnoreCase(name)}
    }

    public static List<String> list() {
        return Arrays.asList(PENDING, OK, WARNING, ERROR);
    }

    public static JobStatus byId(Integer id) {
        def elements = JobStatus.list().findAll { x -> x.id.equals(id) }
        if (elements.size() > 0) {
            return elements.first()
        } else  {
            return null
        }
    }

}