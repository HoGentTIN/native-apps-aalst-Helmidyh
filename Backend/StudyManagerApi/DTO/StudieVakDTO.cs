using StudyAPI.Models.Domain;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace StudyAPI.DTO {
    public class StudieVakDTO {
        public int studieVakId { get; set; }
        [Required]
        public string name { get; set; }
        [Required]
        public int aantalTasks { get; set; }
        [Required]
        public int gebruikerId { get; set; }

        public void updateFromModel(StudieVak vak) {
            vak.Name = this.name;
            vak.AantalTasks = this.aantalTasks;
            vak.GebruikerId = this.gebruikerId;
        }
    }
}
