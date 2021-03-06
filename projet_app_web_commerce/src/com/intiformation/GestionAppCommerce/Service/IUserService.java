package com.intiformation.GestionAppCommerce.Service;

import java.util.List;

import com.intiformation.GestionAppCommerce.Modele.User;

/**
 * interface de la couche service pour l'user permettant d'afficher les fonctionnalités de l'user
 * @author giovanni
 *
 */

public interface IUserService {
	
	public boolean ajoutUser(User pUser);
	
	public boolean updateUser(User pUser);
	
	public boolean deleteUser(Integer pIdUser);

	public List<User> getAll();

	public User getById(Integer pIdUser);
	
	public User getByMdp(String pMotDePasse);
	
	public boolean isUserExists(String pIdentifiant, String pMotDePasse);
	
	public boolean attribuerRole(String pRoleName, Integer pIdUser);
	
	public boolean updateRole (String pRoleName, Integer pIdUser);

	boolean deleteRole(Integer pIdUser);
	
	
}//END INTERFACE
