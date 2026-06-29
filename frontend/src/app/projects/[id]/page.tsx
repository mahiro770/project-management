import Link from "next/link";
import { notFound } from "next/navigation";
import { getProject, deleteProject } from "@/lib/api";
import StatusBadge from "@/components/StatusBadge";

type Props = { params: Promise<{ id: string }> };

export default async function ProjectDetailPage({ params }: Props) {
  const { id } = await params;
  const project = await getProject(Number(id)).catch(() => null);

  if (!project) notFound();

  return (
    <div className="max-w-2xl mx-auto px-4 py-8">
      <div className="flex items-center gap-2 mb-6">
        <Link href="/" className="text-gray-500 hover:text-gray-700 text-sm">
          ← 一覧に戻る
        </Link>
      </div>

      <div className="bg-white border border-gray-200 rounded-lg p-6 space-y-4">
        <div className="flex items-start justify-between gap-4">
          <h1 className="text-2xl font-bold text-gray-800">{project.title}</h1>
          <StatusBadge status={project.status} />
        </div>

        <dl className="grid grid-cols-1 gap-3 text-sm">
          <div className="flex gap-2">
            <dt className="w-28 text-gray-500 shrink-0">会社名</dt>
            <dd className="text-gray-800">{project.clientName}</dd>
          </div>
          <div className="flex gap-2">
            <dt className="w-28 text-gray-500 shrink-0">必須スキル</dt>
            <dd className="text-gray-800">{project.requiredSkills || "—"}</dd>
          </div>
          <div className="flex gap-2">
            <dt className="w-28 text-gray-500 shrink-0">勤務地</dt>
            <dd className="text-gray-800">{project.location || "—"}</dd>
          </div>
          <div className="flex gap-2">
            <dt className="w-28 text-gray-500 shrink-0">金額</dt>
            <dd className="text-gray-800">
              {project.priceMin ?? "?"}〜{project.priceMax ?? "?"}万円
            </dd>
          </div>
          <div className="flex gap-2">
            <dt className="w-28 text-gray-500 shrink-0">登録日時</dt>
            <dd className="text-gray-500">{project.createdAt}</dd>
          </div>
          <div className="flex gap-2">
            <dt className="w-28 text-gray-500 shrink-0">更新日時</dt>
            <dd className="text-gray-500">{project.updatedAt}</dd>
          </div>
        </dl>
      </div>

      <div className="flex gap-3 mt-6">
        <Link
          href={`/projects/${project.id}/edit`}
          className="bg-blue-600 text-white px-5 py-2 rounded-md hover:bg-blue-700 text-sm"
        >
          編集する
        </Link>
        <form
          action={async () => {
            "use server";
            await deleteProject(project.id);
          }}
        >
          <button
            type="submit"
            className="border border-red-300 text-red-600 px-5 py-2 rounded-md hover:bg-red-50 text-sm"
          >
            削除する
          </button>
        </form>
      </div>
    </div>
  );
}
