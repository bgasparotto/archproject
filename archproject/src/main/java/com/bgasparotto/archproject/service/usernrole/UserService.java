package com.bgasparotto.archproject.service.usernrole;

import com.bgasparotto.archproject.model.Authentication;
import com.bgasparotto.archproject.model.User;
import com.bgasparotto.archproject.service.GenericService;
import com.bgasparotto.archproject.service.exception.InvalidVerificationCodeException;
import com.bgasparotto.archproject.service.exception.ServiceException;

/**
 * Services for the {@link User} entity.
 * 
 * @author Bruno Gasparotto
 * 
 * @see GenericService
 *
 */
public interface UserService extends GenericService<User> {

	/**
	 * Find an user by its {@code username}.
	 * 
	 * @param username
	 *            The {@code username} of the user to find
	 * @return User corresponding to the given {@code username}, or {@code null}
	 *         if the is no User on the system corresponding to the given
	 *         {@code username}
	 * @throws ServiceException
	 *             if an error occurs when trying to find an user by its
	 *             {@code username}
	 */
	User findByUsername(String username) throws ServiceException;

	/**
	 * Find an user by its {@code email}.
	 * 
	 * @param email
	 *            The {@code email} of the user to find
	 * @return User corresponding to the given {@code email}, or {@code null} if
	 *         the is no User on the system corresponding to the given
	 *         {@code email}
	 * @throws ServiceException
	 *             if an error occurs when trying to find an user by its
	 *             {@code email}
	 */
	User findByEmail(String email) throws ServiceException;

	/**
	 * Registers a new user on the system, encrypting it's password before
	 * requesting the persistence operation.
	 * 
	 * @param authentication
	 *            The authentication of the user being registered
	 * @return The {@code id} generated for the registered user
	 * 
	 * @throws IllegalArgumentException
	 *             if the {@code Authentication} object is {@code null}
	 * @throws IllegalStateException
	 *             if one of the user's required fields to register is
	 *             {@code null} or empty. Such fields are {@code username},
	 *             {@code password} and {@code email} at this time
	 * @throws ServiceException
	 *             if:
	 *             <ul>
	 *             <li>The {@code username} already exists;</li>
	 *             <li>The {@code email} is already in use;</li>
	 *             <li>Any error persistence error occurred during the register
	 *             operation.</li>
	 *             </ul>
	 */
	Long register(Authentication authentication) throws ServiceException;

	/**
	 * <p>
	 * Tries an authentication operation with the given credentials.
	 * </p>
	 * <p>
	 * This method accepts both {@code username} and {@code email} as input to
	 * find the a user on the system. This method will internally choose the
	 * correct criteria to find the user.
	 * </p>
	 * 
	 * @param usernameOrEmail
	 *            The {@code username} or {@code email} of the User to
	 *            authenticate against the {@code password}
	 * @param password
	 *            The plain text {@code password} to authenticate the
	 *            {@code username}
	 * @return {@code User} instance if the given combination of
	 *         {@code usernameOrEmail} and {@code password} are valid,
	 *         {@code null} otherwise
	 */
	User authenticate(String usernameOrEmail, String password);
	
	/**
	 * <p>
	 * Changes an user's password if the provided {@code oldPassword} is
	 * equivalent to the user's current password.
	 * </p>
	 * <p>
	 * This method accepts both {@code username} and {@code email} as input to
	 * find the a user on the system. This method will internally choose the
	 * correct criteria to find the user.
	 * </p>
	 * 
	 * 
	 * @param usernameOrEmail
	 *            The {@code username} or {@code email} of the User to have its
	 *            password changed
	 * @param oldPassword
	 *            The plain text <strong>old</strong> password of the user
	 * @param newPassword
	 *            The plain text <strong>new</strong> password of the user
	 * @return The updated {@code User} if the operation succeeds, {@code null}
	 *         otherwise
	 * 
	 * @throws ServiceException
	 *             if the operation fails when trying to update the user
	 *             credentials
	 */
	User changePassword(String usernameOrEmail, String oldPassword,
			String newPassword) throws ServiceException;
	
	/**
	 * Validates an user on the system, changing its roles in order to give him
	 * access to log in.
	 * 
	 * @param username
	 *            The {@code username} of the User to be validated
	 * @param verificationCode
	 *            The verification code to validate the User
	 * @return The updated {@code User} if the operation succeeds, {@code null}
	 *         otherwise
	 * 
	 * @throws InvalidVerificationCodeException
	 *             if the verification code provided is invalid
	 * @throws ServiceException
	 *             if the operation fails when trying to update the user
	 *             credentials or trying to find its registration details
	 */
	User validate(String username, String verificationCode)
			throws InvalidVerificationCodeException, ServiceException;
}