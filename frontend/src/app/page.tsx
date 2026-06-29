import Link from "next/link";
import { getProjects } from "@/lib/api";
import StatusBadge from "@/components/StatusBadge";
import { Project } from "@/types/project";

export default async function HomePage() {
  let projects: Project[] = [];
  let fetchError = "";

  try {
    projects = await getProjects();
  } catch {
    fetchError = "バックエンドに接続できませんでした。";
  }

  return (
    <div className="max-w-4xl mx-auto px-4 py-8">
      <div className="flex items-center justify-between mb-6">
        <h1 className="text-2xl font-bold text-gray-800">案件一覧</h1>
        <Link
          href="/projects/new"
          className="bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700 text-sm"
        >
          + 案件登録
        </Link>
      </div>

      {fetchError && (
        <p className="text-red-600 bg-red-50 border border-red-200 rounded-md p-4 text-sm">
          {fetchError}
        </p>
      )}

      {projects.length === 0 && !fetchError && (
        <p className="text-gray-500 text-center py-12">案件がありません</p>
      )}

      <div className="space-y-3">
        {projects.map((project) => (
          <Link
            key={project.id}
            href={`/projects/${project.id}`}
            className="block border border-gray-200 rounded-lg p-4 hover:border-blue-400 hover:shadow-sm transition"
          >
            <div className="flex items-start justify-between gap-4">
              <div className="min-w-0">
                <p className="font-semibold text-gray-800 truncate">{project.title}</p>
                <p className="text-sm text-gray-500 mt-0.5">{project.clientName}</p>
                {project.location && (
                  <p className="text-sm text-gray-400 mt-0.5">📍 {project.location}</p>
                )}
              </div>
              <div className="flex flex-col items-end gap-1 shrink-0">
                <StatusBadge status={project.status} />
                {(project.priceMin || project.priceMax) && (
                  <p className="text-sm text-gray-600">
                    {project.priceMin ?? "?"}〜{project.priceMax ?? "?"}万円
                  </p>
                )}
              </div>
            </div>
          </Link>
        ))}
      </div>
    </div>
  );
}
