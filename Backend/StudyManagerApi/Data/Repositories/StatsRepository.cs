using Microsoft.EntityFrameworkCore;
using StudyAPI.Models.Domain;
using StudyAPI.Models.Domain.Repositories;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace StudyAPI.Data.Repositories {
    public class StatsRepository : IStatsRepository {

        private readonly DbSet<StudieVakHistory> _studieVakHistories;
        private readonly ApplicationDbContext _context;

        public StatsRepository(ApplicationDbContext context) {
            this._context = context;
            this._studieVakHistories = context.StudieVakHistories;

        }
        public void Add(StudieVakHistory item) {
            _studieVakHistories.Add(item);
        }

        public IEnumerable<StudieVakHistory> GetAll() {
            return this._studieVakHistories.AsNoTracking();
        }

        public StudieVakHistory GetById(int id) {
            return this._studieVakHistories.SingleOrDefault(x => x.StudieVakHistoryId == id);
        }

        public void Remove(StudieVakHistory item) {
            this._studieVakHistories.Remove(item);
        }

        public void SaveChanges() {
            _context.SaveChanges();
        }

        public void update(StudieVakHistory history) {
            this._studieVakHistories.Update(history);
        }
    }
}
