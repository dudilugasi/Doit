package ac.shenkar.software.doit.dal;
import ac.shenkar.software.doit.bl.LoginListener;
import ac.shenkar.software.doit.bl.TaskUpdateListener;
import ac.shenkar.software.doit.common.TaskItem;
import com.parse.ParseFile;
import java.util.List;

/**
 * class IDataAccess
 */
public interface IDataAccess {

    void getTasks(int orderbyColumn);

    void getWaitingTasks();

    void getTasksForMember(String member,int orderbyColumn);

    void getWaitingTasksForMember(String member);

    void addTask(TaskItem task);

    void removeTask(TaskItem task);

    void updateTask(TaskItem task);
    void updateTask(TaskItem task,ParseFile file);

    void setTaskUpdateListener(TaskUpdateListener listener);
    void updateListeners(List<TaskItem> taskItems, int code);

    void setLoginListener(LoginListener listener);
    void updateLoginListener(int code);

    void login(String username , String password);

    void getTaskById(String id);
}
