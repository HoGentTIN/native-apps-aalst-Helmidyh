using Microsoft.AspNetCore.Identity;

namespace StudyAPI.Models.Domain {
	public class User: IdentityUser<int> {

		/// <summary>
		/// Base64 encoded image string
		/// </summary>
		public string Picture { get; set; }

		public override string Email { 
			get => base.Email; 
			set {
				base.Email = value;
				base.UserName = value; // Required for Auth
			}
		}
        public User() {

        }
        public User(string em) {
            this.Email = em;

        }
	}
}
