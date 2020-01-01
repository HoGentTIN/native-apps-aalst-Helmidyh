using Microsoft.EntityFrameworkCore;
using StudyAPI.Models.Domain;
using StudyAPI.Models.Domain.Repositories;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace StudyAPI.Data.Repositories {
    public class StudieTaskRepository : IStudieTaskRepository {

        private readonly DbSet<StudieTask> _studieTasks;
        private readonly ApplicationDbContext _context;

        public StudieTaskRepository(ApplicationDbContext context) {
            this._context = context;
            this._studieTasks = context.StudieTasks;
        }
        public void Add(StudieTask item) {
            this._studieTasks.Add(item);
        }

        public IEnumerable<StudieTask> GetAll() {
            return this._studieTasks.AsNoTracking();
        }

        public StudieTask GetById(int id) {
            return this._studieTasks.SingleOrDefault(x => x.StudieTaskId == id);
        }

        public void Remove(StudieTask item) {
            this._studieTasks.Remove(item);
        }

        public void SaveChanges() {
            this._context.SaveChanges();
        }

        public void update( StudieTask task) {
        this._studieTasks.Update(task);
        }
    }
}
