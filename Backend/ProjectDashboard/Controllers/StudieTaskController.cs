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
    public class StudieTaskController : ControllerBase {

        private readonly IStudieTaskRepository _studieTaskRepo;
        private readonly IStudieVakRepository _studieVakRepo;

        private readonly UserManager<User> _userManager;

        public StudieTaskController(IStudieTaskRepository re, IStudieVakRepository rev, UserManager<User> userManager) {
            this._studieTaskRepo = re;
            this._studieVakRepo = rev;
            this._userManager = userManager;
        }

        [HttpGet]
        public IEnumerable<StudieTask> GetAll() {
            return this._studieTaskRepo.GetAll();
        }
        [HttpDelete("{id}")]
        public ActionResult<StudieTask> DeleteTask(int id) {
            var task = _studieTaskRepo.GetById(id);
            if (task == null)
                return NotFound();

            _studieTaskRepo.Remove(task);
            _studieTaskRepo.SaveChanges();


            return task;
        }

        [HttpGet]
        [Route("gebruiker/{id}")]
        public async Task<ActionResult<IEnumerable<StudieTask>>> GetStudieTasksByGebruikerAsync(int id) {
            User user = await _userManager.GetUserAsync(User);
            return new ObjectResult(this._studieTaskRepo.GetAll().Select(x=>x.GebruikerId == id)); // ObjectResult moet om CastException te vermijden
        }

        [HttpPost]
        public IActionResult PostStudieTask(StudieTaskDTO model) {
            StudieTask studieTask = new StudieTask();
            model.UpdateFromModel(studieTask);

            StudieVak vak = _studieVakRepo.GetById(studieTask.VakId);
            studieTask.vak = vak;
            _studieTaskRepo.Add(studieTask);
            _studieTaskRepo.SaveChanges();

            return CreatedAtAction(nameof(GetAll),studieTask);
        }

        [HttpPut]
        public IActionResult UpdateStudieTask(StudieTaskDTO model) {
            StudieTask studieTask = new StudieTask();
            model.UpdateFromModel(studieTask);
            studieTask.StudieTaskId = model.studieTaskId;

            _studieTaskRepo.update(studieTask);
            _studieTaskRepo.SaveChanges();

            return CreatedAtAction(nameof(GetAll), studieTask);
        }


    }
}