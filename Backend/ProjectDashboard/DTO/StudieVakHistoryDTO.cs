using StudyAPI.Models.Domain;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace StudyAPI.DTO {
    public class StudieVakHistoryDTO {

        public int studieVakHistoryId { get; set; }
        [Required]
        public string studieVakHistoryName { get; set; }
        [Required]
        public int aantalTasks { get; set; }
        [Required]
        public long totaleStudieTijd { get; set; }
        [Required]
        public int gebruikerId { get; set; }
        public void updateFromModel(StudieVakHistory history) {
            history.StudieVakHistoryName = this.studieVakHistoryName;
            history.AantalTasks = this.aantalTasks;
            history.TotaleStudieTijd = this.totaleStudieTijd;
            history.GebruikerId = this.gebruikerId;
        }
    }
}
