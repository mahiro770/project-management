"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import { Project } from "@/types/project";

type FormData = {
  title: string;
  clientName: string;
  requiredSkills: string;
  location: string;
  priceMin: string;
  priceMax: string;
  status: string;
};

type Props = {
  initialData?: Partial<Project>;
  onSubmit: (data: FormData) => Promise<void>;
  submitLabel: string;
};

export default function ProjectForm({ initialData, onSubmit, submitLabel }: Props) {
  const router = useRouter();
  const [form, setForm] = useState<FormData>({
    title: initialData?.title ?? "",
    clientName: initialData?.clientName ?? "",
    requiredSkills: initialData?.requiredSkills ?? "",
    location: initialData?.location ?? "",
    priceMin: initialData?.priceMin?.toString() ?? "",
    priceMax: initialData?.priceMax?.toString() ?? "",
    status: initialData?.status ?? "OPEN",
  });
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    setForm((prev) => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError("");
    setLoading(true);
    try {
      await onSubmit(form);
      router.push("/");
    } catch (err) {
      setError(err instanceof Error ? err.message : "エラーが発生しました");
    } finally {
      setLoading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-5">
      {error && <p className="text-red-600 text-sm">{error}</p>}

      <div>
        <label className="block text-sm font-medium text-gray-700 mb-1">案件名 *</label>
        <input
          name="title"
          value={form.title}
          onChange={handleChange}
          required
          className="w-full border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
      </div>

      <div>
        <label className="block text-sm font-medium text-gray-700 mb-1">会社名 *</label>
        <input
          name="clientName"
          value={form.clientName}
          onChange={handleChange}
          required
          className="w-full border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
      </div>

      <div>
        <label className="block text-sm font-medium text-gray-700 mb-1">必須スキル</label>
        <input
          name="requiredSkills"
          value={form.requiredSkills}
          onChange={handleChange}
          className="w-full border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
      </div>

      <div>
        <label className="block text-sm font-medium text-gray-700 mb-1">勤務地</label>
        <input
          name="location"
          value={form.location}
          onChange={handleChange}
          className="w-full border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
      </div>

      <div className="flex gap-4">
        <div className="flex-1">
          <label className="block text-sm font-medium text-gray-700 mb-1">最低金額（万円）</label>
          <input
            name="priceMin"
            type="number"
            value={form.priceMin}
            onChange={handleChange}
            className="w-full border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
        </div>
        <div className="flex-1">
          <label className="block text-sm font-medium text-gray-700 mb-1">最高金額（万円）</label>
          <input
            name="priceMax"
            type="number"
            value={form.priceMax}
            onChange={handleChange}
            className="w-full border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
        </div>
      </div>

      <div>
        <label className="block text-sm font-medium text-gray-700 mb-1">配属状況</label>
        <select
          name="status"
          value={form.status}
          onChange={handleChange}
          className="w-full border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
        >
          <option value="OPEN">募集中</option>
          <option value="FILLED">配属済み</option>
          <option value="CLOSED">終了</option>
        </select>
      </div>

      <div className="flex gap-3 pt-2">
        <button
          type="submit"
          disabled={loading}
          className="bg-blue-600 text-white px-6 py-2 rounded-md hover:bg-blue-700 disabled:opacity-50"
        >
          {loading ? "送信中..." : submitLabel}
        </button>
        <button
          type="button"
          onClick={() => router.back()}
          className="border border-gray-300 px-6 py-2 rounded-md hover:bg-gray-50"
        >
          キャンセル
        </button>
      </div>
    </form>
  );
}
