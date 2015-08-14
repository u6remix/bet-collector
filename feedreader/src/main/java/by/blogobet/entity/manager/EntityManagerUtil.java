package by.blogobet.entity.manager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtil {
	private static EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("jpa");

	public static EntityManagerFactory getEntityManagerFactory() {
		return emf;
	}

	public static void close() {
		emf.close();
	}
}
