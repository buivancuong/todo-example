package com.umbala.cuongbv.todo.ui.main;

import com.umbala.cuongbv.todo.model.Task;

import java.util.List;

/**
 * interface này khai báo ra giao diện View và Presenter của màn hình main.
 * mục đích của việc làm này là để giúp liên kết giữa tầng view và tầng xử lý logic (presenter)
 * trở lên lỏng lẻo hơn, do đó có thể dễ dàng thay thế được tầng view hoặc tầng xử lý logic.
 * ví dụ tầng view đang sử dụng activity có thể bỏ ra để thay thế bằng fragment.
 * ngoài ra việc khai báo các phương thức trong interface sẽ giúp người khác khi
 * xem code có thể dễ dàng hình dung ra chức năng của màn hình.
 * ngoài việc dễ dàng thay thế, sử dụng presenter còn giúp code dễ dàng test sử dụng
 * automatic test. bởi vì tầng view và tầng xử lý logic được tách riêng ra, người ta
 * có thể sử dụng các thư viện mock để test phần xử lý logic mà ko cần tương tác qua view.
 *
 */
public interface MainContractor {

    /**
     * khai báo các công việc của view trong màn hình main
     * tầng view (Hiển thị), là tầng chỉ quan tâm đến việc hiển thị dữ liệu tới người dùng
     * ví dụ với trường hợp dưới đây, lớp View này chứa phương thức showTaskList, phương
     * thức này sẽ quan tâm đến việc làm thế nào để đưa danh sách task lên ui cho người dùng
     * quan sát được, nó không quan tâm rằng danh sách task này được lấy từ đâu ra,
     * đã được xử lý (ví dụ sắp xếp) như thế nào.
     * xem thêm {@link Presenter} và {@link MainActivity} để biết được cách hoạt động của tầng View
     */
    interface View {

        void showLoading();

        void hideLoading();

        void showTaskList(List<Task> tasks);

    }

    /**
     * khai báo các công việc cần làm của tầng trình diễn - logic, trong màn hình main
     * tầng trình diễn (Presenter), là tầng xử lý các logic của model và view
     * ví dụ như lấy danh sách task từ đâu, khi nào cần nói với view là "hãy hiển thị loading"
     * "hãy ẩn loading" hoặc "hãy hiển thị danh sách task này lên màn hình"...
     * ví dụ dễ hiểu trong trường hợp sau: khi người dùng muốn reload lại danh sách nào đó,
     * thì người dùng sẽ ấn vào nút reload, trigger này được truyền đến presenter,
     * giả sử presenter muốn load danh sách từ một API mất thời gian từ 1-10 giây (không
     * biết trước thời gian) thì presenter sẽ bảo view hiển thị lên một cái dialog loading,
     * để thông báo với người dùng là dữ liệu đang được load, sau một thời gian load xong
     * dữ liệu, presenter sẽ bảo với view rằng dữ liệu đã được load xong (Hoặc bị lỗi), hãy ẩn
     * dialog loading đi và hiển thị danh sách dữ liệu lấy được lên màn hình (hiển thị lỗi trong
     * trường hợp load dữ liệu bị lỗi)
     * --> presenter sẽ quyết định lấy dữ liệu như thế nào, ở đâu, xử lý dữ liệu như thế nào
     * và lúc nào thì view cần làm gì, do đó nó được gọi là presenter (tầng trình diễn)
     *
     * xem thêm {@link com.umbala.cuongbv.todo.ui.main.Presenter} để hiểu thêm hoạt động của
     * tầng này
     *
     *
     * bên Edit có 1 itf ShowTask{} nhận dữ liệu từ task cần sửa
     */
    interface Presenter{

        void getTaskList();

        void delTask(String id);

        void updateTask(Task task);

    }

}
