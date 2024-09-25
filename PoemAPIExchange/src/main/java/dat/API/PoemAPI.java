package dat.API;

import dat.config.HibernateConfig;
import io.javalin.Javalin;
import org.hibernate.Session;
import org.hibernate.Transaction;
import dat.entities.Poem;
import dat.dtos.PoemDTO;

import java.util.List;

public class PoemAPI {


    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000);

        app.get("/poems", ctx -> {
            Session session = HibernateConfig.getEntityManagerFactory("poems").createEntityManager().unwrap(Session.class);
            List<Poem> poems = session.createQuery("from Poem", Poem.class).list();
            ctx.json(poems.stream().map(PoemDTO::new));
        });

        app.get("/poem/{id}", ctx -> {
            Long id = Long.parseLong(ctx.pathParam("id"));
            Session session = HibernateConfig.getEntityManagerFactory("poems").createEntityManager().unwrap(Session.class);
            Poem poem = session.get(Poem.class, id);
            if (poem != null) {
                ctx.json(new PoemDTO(poem));
            } else {
                ctx.status(404).result("Poem not found");
            }
        });

        app.post("/poem", ctx -> {
            PoemDTO poemDTO = ctx.bodyAsClass(PoemDTO.class);
            Poem poem = new Poem();
            poem.setTitle(poemDTO.getTitle());
            poem.setContent(poemDTO.getContent());
            poem.setAuthor(poemDTO.getAuthor());

            Session session = HibernateConfig.getEntityManagerFactory("poems").createEntityManager().unwrap(Session.class);
            Transaction tx = session.beginTransaction();
            session.save(poem);
            tx.commit();

            ctx.status(201).json(new PoemDTO(poem));
        });

        app.put("/poem/{id}", ctx -> {
            Long id = Long.parseLong(ctx.pathParam("id"));
            Session session = HibernateConfig.getEntityManagerFactory("poems").createEntityManager().unwrap(Session.class);
            Poem poem = session.get(Poem.class, id);

            if (poem != null) {
                PoemDTO poemDTO = ctx.bodyAsClass(PoemDTO.class);
                poem.setTitle(poemDTO.getTitle());
                poem.setContent(poemDTO.getContent());
                poem.setAuthor(poemDTO.getAuthor());

                Transaction tx = session.beginTransaction();
                session.update(poem);
                tx.commit();

                ctx.json(new PoemDTO(poem));
            } else {
                ctx.status(404).result("Poem not found");
            }
        });

        app.delete("/poem/{id}", ctx -> {
            Long id = Long.parseLong(ctx.pathParam("id"));
            Session session = HibernateConfig.getEntityManagerFactory("poems").createEntityManager().unwrap(Session.class);
            Poem poem = session.get(Poem.class, id);
            if (poem != null) {
                Transaction tx = session.beginTransaction();
                session.delete(poem);
                tx.commit();
                ctx.status(204);
            } else {
                ctx.status(404).result("Poem not found");
            }
        });
    }
}
