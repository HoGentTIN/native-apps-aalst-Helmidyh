
using Microsoft.AspNetCore.Identity;
using StudyAPI.Models.Domain;
using System;
using System.Security.Claims;
using System.Threading.Tasks;

namespace StudyAPI.Data {
	public class AppDataInitializer {

		private readonly ApplicationDbContext _context;
		private readonly UserManager<User> _userManager;

		public AppDataInitializer(ApplicationDbContext context, UserManager<User> userManager) {
			_context = context;
			_userManager = userManager;
		}

		public async Task InitializeData(bool isDev) {
			_context.Database.EnsureDeleted();

			if (_context.Database.EnsureCreated()) {
                User admin = new User("user@testing.be");
                await _userManager.CreateAsync(admin, "TestWachtwoord");
                await _userManager.AddClaimAsync(admin, new Claim(ClaimTypes.Role, "User"));
                _context.SaveChanges();
            }
        }
	}
}
