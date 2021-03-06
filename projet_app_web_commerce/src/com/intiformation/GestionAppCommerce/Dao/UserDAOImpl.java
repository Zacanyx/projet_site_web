package com.intiformation.GestionAppCommerce.Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.intiformation.GestionAppCommerce.Modele.User;

/**
 * Classe qui implémente la couche DAO de l'User
 * 
 * @author giovanni
 *
 */
public class UserDAOImpl implements IUserDAO {

	PreparedStatement ps = null;
	ResultSet rs = null;

	@Override
	public boolean add(User pUser) {

		try {
			// def de la requête
			String requeteAdd = "INSERT INTO users (identifiant,password,actived) VALUES (?,?,?)";

			String requeteAddRole = "INSERT INTO role (roleName,userID) VALUES (?, last_insert_id())";

			// envoie requete
			ps = this.connection.prepareStatement(requeteAdd);

			// def des param
			ps.setString(1, pUser.getUserName());
			ps.setString(2, pUser.getPassword());
			ps.setBoolean(3, pUser.isActived());

			// envoi requete
			int verifAdd = ps.executeUpdate();

			if (verifAdd == 1) {
				// def 2ème requete
				ps = this.connection.prepareStatement(requeteAddRole);

				// def des param
				ps.setString(1, pUser.getRoleName());

				// envoi requete
				int verifAddRole = ps.executeUpdate();

				// verif
				return verifAdd + verifAddRole == 2;
			}

		} catch (Exception e) {
			System.out.println("... add() : Erreur lors de l'ajout d'un user dans UserDAOImpl ");
		} finally {
			try {
				ps.close();
			} catch (Exception e) {

			} // END CATCH
		} // END FINALLY
		return false;

	}// END add()

	@Override
	public boolean update(User pUser) {

		try {

			// envoie requete
			ps = this.connection
					.prepareStatement("UPDATE users SET identifiant = ? ,password = ? ,actived = ? WHERE idUser = ?");

			// def des param
			ps.setString(1, pUser.getUserName());
			ps.setString(2, pUser.getPassword());
			ps.setBoolean(3, pUser.isActived());
			ps.setInt(4, pUser.getIdUser());

			// envoi requete
			int verifUpdate = ps.executeUpdate();

			if (verifUpdate == 1) {

				// 2ème requete
				ps = this.connection.prepareStatement("UPDATE role SET roleName = ? WHERE userID = ? ");
				ps.setString(1, pUser.getRoleName());
				ps.setInt(2, pUser.getIdUser());

				int verifUpdateRole = ps.executeUpdate();

				// verif
				return verifUpdate + verifUpdateRole == 2;
			}

		} catch (Exception e) {
			System.out.println("... update() : Erreur lors de la modification d'un user dans UserDAOImpl ");
		} finally {
			try {
				if (ps != null)
					ps.close();

			} catch (Exception e) {

			} // END CATCH
		} // END FINALLY
		return false;
	}

	@Override
	public boolean delete(Integer pIdUser) {

		try {

			// envoie requete
			ps = this.connection.prepareStatement("DELETE FROM users WHERE idUser = ?");

			// def des param
			ps.setInt(1, pIdUser);

			// envoi requete
			int verifDelete = ps.executeUpdate();

			// verif
			return verifDelete == 1;

		} catch (Exception e) {
			System.out.println("... delete() : Erreur lors de la suppression d'un user dans UserDAOImpl ");
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (Exception e) {

			} // END CATCH
		} // END FINALLY
		return false;
	}

	@Override
	public List<User> getAll() {

		try {

			// preparation de la requête
			ps = this.connection.prepareStatement("SELECT idUser, identifiant, password, actived, roleName\n"
					+ "FROM users AS u\n" + "INNER JOIN role AS r\n" + "ON u.idUser = r.userID;");

			// envoie et exécution de la requete
			rs = ps.executeQuery();

			// recup des données
			List<User> listeUser = new ArrayList<>();
			while (rs.next()) {

				User user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getString(5));

				listeUser.add(user);
			} // END WHILE

			return listeUser;

		} catch (Exception e) {
			System.out.println("... getAll() : Erreur lors de la récupération de liste des users dans UserDAOImpl ");
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (Exception e) {

			} // END CATCH
		} // END FINALLY

		return null;
	}

	@Override
	public User getById(Integer pIdUser) {

		try {

			// création requete requete
			ps = this.connection.prepareStatement("SELECT idUser, identifiant, password, actived, roleName\n" + 
					"FROM users AS u\n" + 
					"INNER JOIN role AS r\n" + 
					"ON u.idUser = r.userID\n" + 
					"WHERE idUser=?");
			
			// passage de param
			ps.setInt(1, pIdUser);

			// envoie et exécution de la requete
			rs = ps.executeQuery();

			// recup des données
			rs.next();

			User user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getString(5));

			return user;

		} catch (Exception e) {
			System.out.println(
					"... getById() : Erreur lors de la récupération de l'user en fonction de son ID dans UserDAOImpl ");
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (Exception e) {

			} // END CATCH
		} // END FINALLY
		return null;

	}// END getById()

	@Override
	public User getByMdp(String pMotDePasse) {

		try {

			// création requete requete
			ps = this.connection.prepareStatement("SELECT idUser, identifiant, password, actived, roleName\n"
					+ "	FROM users AS u\n" + "                   "
					+ " INNER JOIN role AS r\n"
					+ " ON u.idUser = r.userID\n"
					+ "	WHERE password= ?");

			// passage de param
			ps.setString(1, pMotDePasse);

			// envoie et exécution de la requete
			rs = ps.executeQuery();

			// recup des données
			rs.next();

			User user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getString(5));

			return user;

		} catch (Exception e) {
			System.out.println(
					"... getByMdp() : Erreur lors de la récupération de l'user en fonction de son Mdp dans UserDAOImpl ");
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (Exception e) {

			} // END CATCH
		} // END FINALLY
		return null;
	}

	@Override
	public boolean isUserExists(String pIdentifiant, String pMotDePasse) {

		try {
			// preparation requete
			ps = this.connection
					.prepareStatement("SELECT COUNT(idUser) FROM users WHERE identifiant = ? AND password = ?");

			// passage de param
			ps.setString(1, pIdentifiant);
			ps.setString(2, pMotDePasse);

			// envoie et exécution de la requête
			rs = ps.executeQuery();

			// initiliation tête de lecture
			rs.next();

			// recup donnée
			int verifCo = rs.getInt(1);

			// verif
			return verifCo == 1;

		} catch (SQLException e) {
			System.out.println(" isUserExists() : Erreur lors de la connexion de l'utilisateur dans UserDAOImpl");
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (Exception e) {

			} // END CATCH
		} // END FINALLY

		return false;
	}// END isUserExists

	@Override
	public boolean attribuerRole(String pRoleName, Integer pIdUser) {

		try {
			// preparation requete
			ps = this.connection.prepareStatement("INSERT INTO role (roleName,userID) VALUES (?,?)");

			// passage de param
			ps.setString(1, pRoleName);
			ps.setInt(2, pIdUser);

			// envoie et exécution requete
			int verifAttribRole = ps.executeUpdate();

			// verif
			return verifAttribRole == 1;

		} catch (SQLException e) {
			System.out.println(" attribuerRole() : Erreur lors de l'attribution d'un rôle à un user dans UserDAOImpl");
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();

			} catch (Exception e) {

			} // END CATCH

		} // END FINALLY
		return false;

	}// END attribuerRole

	@Override
	public boolean updateRole(String pRoleName, Integer pIdUser) {

		try {
			// preparation requete
			ps = this.connection.prepareStatement("UPDATE role SET roleName =? ,userID=?");

			// passage de param
			ps.setString(1, pRoleName);
			ps.setInt(2, pIdUser);

			// envoie et exécution requete
			int verifModifRole = ps.executeUpdate();

			// verif
			return verifModifRole == 1;

		} catch (SQLException e) {
			System.out.println(" udpate() : Erreur lors de la modification d'un rôle à un user dans UserDAOImpl");
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();

			} catch (Exception e) {

			} // END CATCH

		} // END FINALLY

		return false;
	}

	@Override
	public boolean deleteRole(Integer pIdUser) {

		try {
			// preparation requete
			ps = this.connection.prepareStatement("DELETE FROM role WHERE userID = ?");

			// passage de param
			ps.setInt(1, pIdUser);

			// envoie et exécution requete
			int verifSuppRole = ps.executeUpdate();

			// verif
			return verifSuppRole == 1;

		} catch (SQLException e) {
			System.out.println(" deleteRole() : Erreur lors de la suppression d'un rôle à un user dans UserDAOImpl");
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();

			} catch (Exception e) {

			} // END CATCH

		} // END FINALLY

		return false;
	}

}// END CLASS
