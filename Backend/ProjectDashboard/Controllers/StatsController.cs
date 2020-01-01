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
    public class StatsController : ControllerBase {

        private readonly IStatsRepository _statsRepository;
        private readonly UserManager<User> _userManager;

        public StatsController(IStatsRepository re,UserManager<User> usr) {
            this._statsRepository = re;
            this._userManager = usr;
        }

        [HttpGet]
        public IEnumerable<StudieVakHistory> GetAll() {
            return this._statsRepository.GetAll();
        }


        [HttpGet]
        [Route("gebruiker/{id}")]
        public async Task<ActionResult<IEnumerable<StudieVakHistory>>> GetStatsByGebruikerAsync(int id) {
            User user = await _userManager.GetUserAsync(User);
            return new ObjectResult(this._statsRepository.GetAll().Select(x => x.GebruikerId == id)); // ObjectResult moet om CastException te vermijden
        }


        [HttpPost]
        public IActionResult PostStudieVakHistory(StudieVakHistoryDTO model) {
            StudieVakHistory history = new StudieVakHistory();
            model.updateFromModel(history);
            history.StudieVakHistoryId = model.studieVakHistoryId;
            _statsRepository.Add(history);
            _statsRepository.SaveChanges();

            return CreatedAtAction(nameof(GetAll), history);
        }

        [HttpPut]
        public IActionResult UpdateHistory(StudieVakHistoryDTO model) {
            StudieVakHistory history = new StudieVakHistory();
            model.updateFromModel(history);
            history.StudieVakHistoryId = model.studieVakHistoryId;

            _statsRepository.update(history);
            _statsRepository.SaveChanges();

            return CreatedAtAction(nameof(GetAll), history);
        }

    }
}