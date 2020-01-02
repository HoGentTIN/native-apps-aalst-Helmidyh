using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace StudyAPI.Models.Domain.Repositories {
    public interface IStatsRepository : IBaseRepository<StudieVakHistory> {
        void update(StudieVakHistory history);
    }
}
