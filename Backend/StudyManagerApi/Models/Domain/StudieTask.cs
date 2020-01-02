using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace StudyAPI.Models.Domain {
    public class StudieTask {

        public int StudieTaskId { get; set; }
        public string StudieTaskTitle { get; set; }
        public long TotalTaskDuration { get; set; }
        public long RemainingTaskTime { get; set; }
        public int VakId { get; set; }
        public string VakName { get; set; }
        public StudieVak vak { get; set; }
        public int GebruikerId { get; set; }

        public StudieTask() {

        }
        public StudieTask(string taskTitle, long totalDur,long remDur, string vakName,int gebruikerId) {
            this.StudieTaskTitle = taskTitle;
            this.TotalTaskDuration = totalDur;
            this.RemainingTaskTime = remDur;
            this.VakName = vakName;
            this.GebruikerId = gebruikerId;
        }
    }
}
