using Microsoft.EntityFrameworkCore;
using StudyAPI.Models.Domain;
using StudyAPI.Models.Domain.Repositories;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace StudyAPI.Data.Repositories {

    public class StudieVakRepository : IStudieVakRepository {
        private readonly DbSet<StudieVak> _studieVakken;
        private readonly ApplicationDbContext _context;

        public StudieVakRepository(ApplicationDbContext context) {
            this._context = context;
            this._studieVakken = context.StudieVakken;
        }

        public void Add(StudieVak item) {
            this._studieVakken.Add(item);
        }

        public IEnumerable<StudieVak> GetAll() {
            return this._studieVakken.AsNoTracking();
        }

        public StudieVak GetById(int id) {
            return this._studieVakken.SingleOrDefault(x => x.StudieVakId == id);
        }

        public void Remove(StudieVak item) {
            this._studieVakken.Remove(item);
        }

        public void SaveChanges() {
            this._context.SaveChanges();
        }
        public void update(StudieVak vak) {
            this._studieVakken.Update(vak);
        }
    }
}
