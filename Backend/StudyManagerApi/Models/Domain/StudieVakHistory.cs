using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace StudyAPI.Models.Domain {
    public class StudieVakHistory {
        public int StudieVakHistoryId { get; set; }
        public string StudieVakHistoryName { get; set; }
        public int AantalTasks { get; set; }
        public long TotaleStudieTijd { get; set; }
        public int GebruikerId { get; set; }
        public StudieVakHistory() {

        }
        public StudieVakHistory(string histName,int aantalTsks,long totStudieTijd,int gebruikerId) {
            this.StudieVakHistoryName = histName;
            this.AantalTasks = aantalTsks;
            this.TotaleStudieTijd = totStudieTijd;
            this.GebruikerId = gebruikerId;
        }
    
    }
}
