﻿using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using Microsoft.IdentityModel.Tokens;
using StudyAPI.DTO.Identity;
using StudyAPI.Models.Domain;
using System;
using System.Collections.Generic;
using System.IdentityModel.Tokens.Jwt;
using System.Linq;
using System.Security.Claims;
using System.Text;
using System.Threading.Tasks;

namespace StudyAPI.Controllers {

	[ApiConventionType(typeof(DefaultApiConventions))]
	[Produces("application/json")]
	[Route("api/[controller]")]
	[ApiController]
	public class AuthController : ControllerBase {

		private readonly SignInManager<User> _signInManager;
		private readonly UserManager<User> _userManager;

		private readonly IConfiguration _config;

		public AuthController(
			SignInManager<User> signInManager,
			UserManager<User> userManager,
			IConfiguration config) {

			this._signInManager = signInManager;
			this._userManager = userManager;
			this._config = config;
		}

		// POST: api/auth/login
		[HttpPost("login")]
		public async Task<IActionResult> PostLogin(LoginDTO model) {
			User user = await _userManager.FindByEmailAsync(model.Email);
			if (user != null) {
				Microsoft.AspNetCore.Identity.SignInResult result = await _signInManager.CheckPasswordSignInAsync(user, model.Password, false);
				if (result.Succeeded) {
					string token = await GetTokenAsync(user);

					return Ok(new {
						user.Picture,
						Token = token
					});
				}
			}

			return Unauthorized("Invalid email or password");
		}

		private async Task<string> GetTokenAsync(User user) {
			IList<Claim> roles = await _userManager.GetClaimsAsync(user);

			Claim[] userclaims = new[]
			{
			  new Claim("id", user.Id.ToString()), // User Id
			  new Claim("Email", user.UserName),
			  
			  new Claim(ClaimTypes.NameIdentifier, user.Id.ToString())
			};

			List<Claim> claims = new List<Claim>();
			claims.AddRange(userclaims);
			claims.AddRange(roles);

			SymmetricSecurityKey key = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_config["Jwt:Key"]));
			SigningCredentials creds = new SigningCredentials(key, SecurityAlgorithms.HmacSha256);

			JwtSecurityToken token = new JwtSecurityToken(
			  _config["Jwt:Issuer"],
			  _config["Jwt:Issuer"],
			  claims,
			  expires: DateTime.Now.AddMinutes(120),
			  signingCredentials: creds);

			return new JwtSecurityTokenHandler().WriteToken(token);
		}

		// POST: api/auth/refreshtoken
		[HttpPost]
		[Authorize]
		[Route("refreshtoken")]
		public async Task<IActionResult> RefreshTokenAsync() {
			User u = await _userManager.GetUserAsync(User);
			string token = await GetTokenAsync(u);

			return Ok(new {
				u.Picture,
				Token = token
			});
		}

		// POST: api/auth/register
		[HttpPost("register")]
		public async Task<IActionResult> PostRegister(RegisterDTO model) {
			User user = new User {
				Picture = model.Picture,
				Email = model.Email,
			};

			IdentityResult result = await _userManager.CreateAsync(user, model.Password);
			if (result.Succeeded) {
				string token = await GetTokenAsync(user);
				return Ok(new {
					user.Picture,
					Token = token
				});
			}

			return BadRequest("Registration failed");
		}

		// POST: api/auth/logout
		[Authorize]
		[HttpPost("logout")]
		public async Task<IActionResult> PostLogout() {
			await _signInManager.SignOutAsync();
			return Ok();
		}
	}
}