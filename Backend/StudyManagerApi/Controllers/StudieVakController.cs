using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using StudyAPI.DTO;
using StudyAPI.Models.Domain;
using StudyAPI.Models.Domain.Repositories;

namespace StudyAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class StudieVakController : ControllerBase {

        private readonly IStudieTaskRepository _studieTaskRepo;
        private readonly IStudieVakRepository _studieVakRepo;
        private readonly UserManager<User> _userManager;

        public StudieVakController(IStudieTaskRepository re, IStudieVakRepository rev, UserManager<User> userManager) {
            this._studieTaskRepo = re;
            this._studieVakRepo = rev;
            this._userManager = userManager;
        }

        [HttpGet]
        public IEnumerable<StudieVak> GetAll() {
            return this._studieVakRepo.GetAll();
        }
        [HttpDelete("{id}")]
        public ActionResult<StudieVak> DeleteVak(int id) {
            var vak = _studieVakRepo.GetById(id);
            if (vak == null)
                return NotFound();

            _studieVakRepo.Remove(vak);
            _studieTaskRepo.SaveChanges();


            return vak;
        }

        [HttpGet]
        [Route("gebruiker/{id}")]
        public async Task<ActionResult<IEnumerable<StudieVak>>> GetStudieVakkenByGebruikerAsync(int id) {
            User user = await _userManager.GetUserAsync(User);
            return new ObjectResult(this._studieVakRepo.GetAll().Select(x => x.GebruikerId == id)); // ObjectResult moet om CastException te vermijden
        }

        [HttpPost]
        public IActionResult PostStudieVak(StudieVakDTO model) {
            StudieVak studieVak = new StudieVak();
            model.updateFromModel(studieVak);

            StudieVak vak = _studieVakRepo.GetById(studieVak.StudieVakId);
            _studieVakRepo.Add(studieVak);
            _studieVakRepo.SaveChanges();
            return CreatedAtAction(nameof(GetAll), studieVak);
        }

        [HttpPut]
        public IActionResult UpdateVak(StudieVakDTO model) {
            StudieVak vak = new StudieVak();
            model.updateFromModel(vak);
            vak.StudieVakId = model.studieVakId;

            _studieVakRepo.update(vak);
            _studieVakRepo.SaveChanges();

            return CreatedAtAction(nameof(GetAll), vak);
        }


    }
}