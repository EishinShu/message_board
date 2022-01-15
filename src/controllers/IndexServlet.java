package controllers; 		//データベースから複数のメッセージ情報を取得するサーブレット

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Message;
import utils.DBUtil;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       EntityManager em = DBUtil.createEntityManager();

       //Message.javaでつけた名前 getAllMessages を ↓createNamedQuery メソッドの引数に指定してあげることで、データベースへの問い合わせを実行
       //その問い合わせ結果を getResultList() メソッドを使ってリスト形式で取得
       //データベースに保存されたデータはHibernateによって自動で Message クラスのオブジェクトになってこのリストの中に格納される
       List<Message> messages = em.createNamedQuery("getAllMessages", Message.class).getResultList();
       //データの登録件数を表示
       // response.getWriter().append(Integer.valueOf(messages.size()).toString());
        em.close();

        request.setAttribute("messages", messages);

        // フラッシュメッセージがセッションスコープにセットされていたら
        // リクエストスコープに保存する（セッションスコープからは削除）
        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }
    }
}
