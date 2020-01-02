using StudyAPI.Models.Domain;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace StudyAPI.DTO {
    public class StudieTaskDTO {

        public int studieTaskId { get; set; }

        [Required]
        public string studieTaskTitle { get; set; }

        [Required]
        public long totalTaskDuration { get; set; }

        [Required]
        public long remainingTaskTime { get; set; }

        [Required]
        public int vakId { get; set; }

        [Required]
        public string vakName { get; set; }

        [Required]
        public int gebruikerId { get; set; }

        public void UpdateFromModel(StudieTask task) {
            task.StudieTaskTitle = this.studieTaskTitle;
            task.TotalTaskDuration = this.totalTaskDuration;
            task.RemainingTaskTime = this.remainingTaskTime;
            task.VakId = this.vakId;
            task.VakName = this.vakName;
            task.GebruikerId = this.gebruikerId;
        }

    }
}
