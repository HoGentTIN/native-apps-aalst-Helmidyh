using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace StudyAPI.Models.Domain {
    public class StudieVak {
        public int StudieVakId { get; set; }
        public string Name { get; set; }
        public int AantalTasks { get; set; }
        public int GebruikerId { get; set; }

        public StudieVak() {

        }
        public StudieVak(string name, int aantalTsks,int gebruikerId) {
            this.Name = name;
            this.AantalTasks = aantalTsks;
            this.GebruikerId = gebruikerId;
        }
    }
}
